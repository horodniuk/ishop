package com.jshop.repository.impl;

import com.jshop.entity.Order;
import com.jshop.framework.factory.JDBCConnectionUtils;
import com.jshop.framework.handler.DefaultListResultSetHandler;
import com.jshop.framework.handler.DefaultUniqueResultSetHandler;
import com.jshop.framework.handler.IntResultSetHandler;
import com.jshop.framework.handler.ResultSetHandler;
import com.jshop.jdbc.JDBCUtils;
import com.jshop.repository.OrderRepository;

import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {
    private final ResultSetHandler<Order> orderResultSetHandler = new DefaultUniqueResultSetHandler<>(Order.class);
    private final ResultSetHandler<List<Order>> ordersResultSetHandler = new DefaultListResultSetHandler<>(Order.class);
    private final ResultSetHandler<Integer> countResultSetHandler = new IntResultSetHandler();

    @Override
    public void create(Order order) {
        Order createdOrder = JDBCUtils.insert(JDBCConnectionUtils.getCurrentConnection(),
                "insert into \"order\" values(nextval('order_seq'),?,?)", orderResultSetHandler, order.getIdAccount(), order.getCreated());
        order.setId(createdOrder.getId());
    }

    @Override
    public Order findById(Long id) {
        return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(), "select * from \"order\" where id=?", orderResultSetHandler, id);
    }

    @Override
    public List<Order> listMyOrders(Integer idAccount, int offset, int limit) {
        return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(), "select * from \"order\" where id_account=? order by id desc limit ? offset ?",
                ordersResultSetHandler, idAccount, limit, offset);
    }

    @Override
    public int countMyOrders(Integer idAccount) {
        return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(), "select count(*) from \"order\" where id_account=?",
                countResultSetHandler, idAccount);
    }
}
