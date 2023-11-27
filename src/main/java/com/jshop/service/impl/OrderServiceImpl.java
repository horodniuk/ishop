package com.jshop.service.impl;

import com.jshop.entity.Account;
import com.jshop.entity.Order;
import com.jshop.entity.OrderItem;
import com.jshop.entity.Product;
import com.jshop.exception.AccessDeniedException;
import com.jshop.exception.InternalServerErrorException;
import com.jshop.exception.ResourceNotFoundException;
import com.jshop.form.ProductForm;
import com.jshop.framework.annotation.Autowired;
import com.jshop.framework.annotation.Component;
import com.jshop.framework.annotation.Value;
import com.jshop.framework.annotation.jdbc.Transactional;
import com.jshop.model.CurrentAccount;
import com.jshop.model.ShoppingCart;
import com.jshop.model.ShoppingCartItem;
import com.jshop.model.SocialAccount;
import com.jshop.repository.AccountRepository;
import com.jshop.repository.OrderItemRepository;
import com.jshop.repository.OrderRepository;
import com.jshop.repository.ProductRepository;
import com.jshop.service.OrderService;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.List;


@Component
public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Value("email.smtp.server")
    private String smtpHost;
    @Value("email.smtp.port")
    private String smtpPort;
    @Value("email.smtp.username")
    private String smtpUsername;
    @Value("email.smtp.password")
    private String smtpPassword;
    @Value("app.host")
    private String host;
    @Value("email.smtp.fromAddress")
    private String fromAddress;

    @Override
    @Transactional
    public void addProductToShoppingCart(ProductForm productForm, ShoppingCart shoppingCart) {
        Product product = productRepository.findById(productForm.getIdProduct());
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
        Account account = accountRepository.findByEmail(socialAccount.getEmail());
            if (account == null) {
                account = new Account(socialAccount.getName(), socialAccount.getEmail());
                accountRepository.create(account);
            }
            return account;
    }

    @Override
    @Transactional(readOnly=false)
    public long makeOrder(ShoppingCart shoppingCart, CurrentAccount currentAccount) {
        validateShoppingCart(shoppingCart);
        Order order = new Order(currentAccount.getId(), new Timestamp(System.currentTimeMillis()));
        orderRepository.create(order);
        for (ShoppingCartItem item : shoppingCart.getItems()) {
            OrderItem tempOrder = new OrderItem(order.getId(), item.getProduct(), item.getCount());
            orderItemRepository.create(tempOrder);
        }
        // sendEmail(currentAccount.getEmail(), order);
        return order.getId();
    }

    private void validateShoppingCart (ShoppingCart shoppingCart) {
        if (shoppingCart == null || shoppingCart.getItems().isEmpty()) {
            throw new InternalServerErrorException("shoppingCart is null or empty");
        }
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
        Order order = orderRepository.findById(id);
        if (order == null) {
            throw new ResourceNotFoundException("Order not found by id: " + id);
        }
        if (!order.getIdAccount().equals(currentAccount.getId())) {
            throw new AccessDeniedException("Account with id=" + currentAccount.getId() + " is not owner for order with id=" + id);
        }
        order.setItems(orderItemRepository.findByIdOrder(id));
        return order;
    }

    @Override
    @Transactional
    public List<Order> listMyOrders(CurrentAccount currentAccount, int page, int limit) {
        int offset = (page - 1) * limit;
        return orderRepository.listMyOrders(currentAccount.getId(), limit, offset);
    }

    @Override
    @Transactional
    public int countMyOrders(CurrentAccount currentAccount) {
        return orderRepository.countMyOrders(currentAccount.getId());
    }
}
