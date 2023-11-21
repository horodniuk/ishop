package com.jshop.repository.impl;

import com.jshop.entity.Producer;
import com.jshop.framework.factory.JDBCConnectionUtils;
import com.jshop.framework.handler.DefaultListResultSetHandler;
import com.jshop.framework.handler.ResultSetHandler;
import com.jshop.jdbc.JDBCUtils;
import com.jshop.repository.ProducerRepository;

import java.util.List;

public class ProducerRepositoryImpl implements ProducerRepository {
    private final ResultSetHandler<List<Producer>> producerListResultSetHandler = new DefaultListResultSetHandler<>(Producer.class);

    @Override
    public List<Producer> listAllProducers() {
        return JDBCUtils.select(JDBCConnectionUtils.getCurrentConnection(),
                "select * from producer order by name", producerListResultSetHandler);
    }
}
