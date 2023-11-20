package com.jshop.service.impl;

import com.jshop.entity.Account;
import com.jshop.entity.Order;
import com.jshop.entity.OrderItem;
import com.jshop.entity.Product;
import com.jshop.exception.AccessDeniedException;
import com.jshop.exception.InternalServerErrorException;
import com.jshop.exception.ResourceNotFoundException;
import com.jshop.form.ProductForm;
import com.jshop.framework.annotation.jdbc.Transactional;
import com.jshop.framework.factory.JDBCConnectionUtils;
import com.jshop.framework.handler.DefaultListResultSetHandler;
import com.jshop.framework.handler.DefaultUniqueResultSetHandler;
import com.jshop.framework.handler.IntResultSetHandler;
import com.jshop.framework.handler.ResultSetHandler;
import com.jshop.jdbc.JDBCUtils;
import com.jshop.model.CurrentAccount;
import com.jshop.model.ShoppingCart;
import com.jshop.model.ShoppingCartItem;
import com.jshop.model.SocialAccount;
import com.jshop.service.OrderService;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final ResultSetHandler<Product> productResultSetHandler = new DefaultUniqueResultSetHandler<>(Product.class);
    private final ResultSetHandler<Account> accountResultSetHandler = new DefaultUniqueResultSetHandler<>(Account.class);
    private final ResultSetHandler<Order> orderResultSetHandler = new DefaultUniqueResultSetHandler<>(Order.class);
    private final ResultSetHandler<List<OrderItem>> orderItemListResultSetHandler = new DefaultListResultSetHandler<>(OrderItem.class);
    private final ResultSetHandler<List<Order>> ordersResultSetHandler = new DefaultListResultSetHandler<>(Order.class);
    private final ResultSetHandler<Integer> countResultSetHandler = new IntResultSetHandler<>();

    private String smtpHost;
    private String smtpPort;
    private String smtpUsername;
    private String smtpPassword;
    private String host;
    private String fromAddress;

    public OrderServiceImpl(ServiceManager serviceManager) {
        super();
        this.smtpHost = serviceManager.getApplicationProperty("email.smtp.server");
        this.smtpPort = serviceManager.getApplicationProperty("email.smtp.port");
        this.smtpUsername = serviceManager.getApplicationProperty("email.smtp.username");
        this.smtpPassword = serviceManager.getApplicationProperty("email.smtp.password");
        this.host = serviceManager.getApplicationProperty("app.host");
        this.fromAddress = serviceManager.getApplicationProperty("email.smtp.fromAddress");
    }

    @Override
    @Transactional
    public void addProductToShoppingCart(ProductForm productForm, ShoppingCart shoppingCart) {
        Product product = JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(), "select p.*, c.name as category, pr.name as producer from product p, producer pr, category c "
                                              + "where c.id=p.id_category and pr.id=p.id_producer and p.id=?",
                productResultSetHandler, productForm.getIdProduct());
        if (product == null) {
            throw new InternalServerErrorException("Product not found by id=" + productForm.getIdProduct());
        }
        shoppingCart.addProduct(product, productForm.getCount());
    }

    @Override
    public void removeProductFromShoppingCart(ProductForm form, ShoppingCart shoppingCart) {
        shoppingCart.removeProduct(form.getIdProduct(), form.getCount());
    }

    @Override
    public String serializeShoppingCart(ShoppingCart shoppingCart) {
        StringBuilder res = new StringBuilder();
        for (ShoppingCartItem item : shoppingCart.getItems()) {
            res.append(item.getProduct().getId()).append("-").append(item.getCount()).append("|");
        }
        if (res.length() > 0) {
            res.deleteCharAt(res.length() - 1);
        }
        return res.toString();
    }

    @Override
    @Transactional
    public ShoppingCart deserializeShoppingCart(String string) {
        ShoppingCart shoppingCart = new ShoppingCart();
        String[] items = string.split("\\|");
        for (String item : items) {
            try {
                String data[] = item.split("-");
                int idProduct = Integer.parseInt(data[0]);
                int count = Integer.parseInt(data[1]);
                addProductToShoppingCart(new ProductForm(idProduct, count), shoppingCart);
            } catch (RuntimeException e) {
                LOGGER.error("Can't add product to ShoppingCart during deserialization: item=" + item, e);
            }
        }
        return shoppingCart.getItems().isEmpty() ? null : shoppingCart;
    }

    @Override
    @Transactional(readOnly=false)
    public CurrentAccount authentificate(SocialAccount socialAccount) {
            Account account = JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(), "select * from account where email=?", accountResultSetHandler, socialAccount.getEmail());
            if (account == null) {
                account = new Account(socialAccount.getName(), socialAccount.getEmail());
                account = JDBCUtils.insert(JDBCConnectionUtils.getCurrentConnection(), "insert into account values (nextval('account_seq'),?,?)",
                        accountResultSetHandler, account.getName(), account.getEmail());
            }
            return account;
    }

    @Override
    @Transactional(readOnly=false)
    public long makeOrder(ShoppingCart shoppingCart, CurrentAccount currentAccount) {
        if (shoppingCart == null || shoppingCart.getItems().isEmpty()) {
            throw new InternalServerErrorException("shoppingCart is null or empty");
        }
        Order order = JDBCUtils.insert(JDBCConnectionUtils.getCurrentConnection(), "insert into \"order\" values(nextval('order_seq'),?,?)", orderResultSetHandler,
                currentAccount.getId(), new Timestamp(System.currentTimeMillis()));
        JDBCUtils.insertBatch(JDBCConnectionUtils.getCurrentConnection(),
                "insert into order_item values(nextval('order_item_seq'),?,?,?)",
                toOrderItemParameterList(order.getId(), shoppingCart.getItems()));
        // sendEmail(currentAccount.getEmail(), order);
        return order.getId();
    }

    private void sendEmail(String emailAddress, Order order) {
        try {
            SimpleEmail email = new SimpleEmail();
            email.setCharset("utf-8");
            email.setHostName(smtpHost);
            email.setSSLOnConnect(true);
            email.setSslSmtpPort(smtpPort);
            email.setFrom(fromAddress);
            email.setAuthenticator(new DefaultAuthenticator(smtpUsername, smtpPassword));
            email.setSubject("New order");
            email.setMsg(host + "/order?id=" + order.getId());
            email.addTo(emailAddress);
            email.send();
        } catch (Exception e) {
            LOGGER.error("Error during send email: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Order findOrderById(long id, CurrentAccount currentAccount) {
        Order order = JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(), "select * from \"order\" where id=?", orderResultSetHandler, id);
        if (order == null) {
            throw new ResourceNotFoundException("Order not found by id: " + id);
        }
        if (!order.getIdAccount().equals(currentAccount.getId())) {
            throw new AccessDeniedException("Account with id=" + currentAccount.getId() + " is not owner for order with id=" + id);
        }
        List<OrderItem> list = JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(),
                "select o.id, o.id_order as id_order, o.id_product, o.count, p.id as pid, p.name, p.description, p.price, p.image_link, c.name as category, pr.name as producer from order_item o, product p, category c, producer pr " +
                "where pr.id=p.id_producer and c.id=p.id_category and o.id_product=p.id and o.id_order=?",
                orderItemListResultSetHandler, id);
        order.setItems(list);
        return order;
    }

    @Override
    @Transactional
    public List<Order> listMyOrders(CurrentAccount currentAccount, int page, int limit) {
        int offset = (page - 1) * limit;
        List<Order> orders = JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(), "select * from \"order\" where id_account=? order by id desc limit ? offset ?",
                ordersResultSetHandler, currentAccount.getId(), limit, offset);
        return orders;
    }

    @Override
    @Transactional
    public int countMyOrders(CurrentAccount currentAccount) {
        return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(), "select count(*) from \"order\" where id_account= ? ",
                countResultSetHandler, currentAccount.getId());
    }

    private List<Object[]> toOrderItemParameterList(long idOrder, Collection<ShoppingCartItem> items) {
        List<Object[]> parametersList = new ArrayList<>();
        for (ShoppingCartItem item : items) {
            parametersList.add(new Object[]{idOrder, item.getProduct().getId(), item.getCount()});
        }
        return parametersList;
    }
}
