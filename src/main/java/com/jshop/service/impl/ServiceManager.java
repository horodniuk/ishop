package com.jshop.service.impl;

import com.jshop.framework.factory.JDBCTransactionalServiceFactory;
import com.jshop.repository.*;
import com.jshop.repository.impl.*;
import com.jshop.service.OrderService;
import com.jshop.service.ProductService;
import com.jshop.service.SocialService;
import com.jshop.service.impl.social.GoogleSocialService;
import jakarta.servlet.ServletContext;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;


public class ServiceManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceManager.class);

    public static ServiceManager getInstance(ServletContext context) {
        ServiceManager instance = (ServiceManager) context.getAttribute("SERVICE_MANAGER");
        if (instance == null) {
            instance = new ServiceManager(context);
            context.setAttribute("SERVICE_MANAGER", instance);
        }
        return instance;
    }

    public ProductService getProductService() {
        return productService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public SocialService getSocialService() {
        return socialService;
    }

    public String getApplicationProperty(String key) {
        String value = applicationProperties.getProperty(key);
        if (value.startsWith("${sysEnv.")) {
            String keyVal = value.replace("${sysEnv.", "").replace("}", "");
            value = System.getenv(keyVal);
        }
        return value;
    }

    public void close() {
        try {
            dataSource.close();
        } catch (SQLException e) {
            LOGGER.error("Close datasource failed: " + e.getMessage(), e);
        }
    }

    final Properties applicationProperties = new Properties();
    final BasicDataSource dataSource;
    final ProductRepository productRepository;
    final ProducerRepository producerRepository;
    final CategoryRepository categoryRepository;
    final AccountRepository accountRepository;
    final OrderItemRepository orderItemRepository;
    final OrderRepository orderRepository;

    private final ProductService productService;
    private final OrderService orderService;
    private final SocialService socialService;


    private ServiceManager(ServletContext context) {
        loadApplicationProperties();
        dataSource = createDataSource();

        productRepository = new ProductRepositoryImpl();
        producerRepository = new ProducerRepositoryImpl();
        categoryRepository = new CategoryRepositoryImpl();
        accountRepository = new AccountRepositoryImpl();
        orderRepository = new OrderRepositoryImpl();
        orderItemRepository = new OrderItemRepositoryImpl();

        productService = (ProductService) JDBCTransactionalServiceFactory.createTransactionalService(dataSource, new ProductServiceImpl(this)) ;
        orderService = (OrderService) JDBCTransactionalServiceFactory.createTransactionalService(dataSource, new OrderServiceImpl(this));
        socialService = new GoogleSocialService(this);
    }

    private BasicDataSource createDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDefaultAutoCommit(false);
        dataSource.setRollbackOnReturn(true);
        dataSource.setDriverClassName(getApplicationProperty("db.driver"));
        dataSource.setUrl(getApplicationProperty("db.url"));
        dataSource.setUsername(getApplicationProperty("db.username"));
        dataSource.setPassword(getApplicationProperty("db.password"));
        dataSource.setInitialSize(Integer.parseInt(getApplicationProperty("db.pool.initSize")));
        dataSource.setMaxTotal(Integer.parseInt(getApplicationProperty("db.pool.maxSize")));
        return dataSource;
    }

    private void loadApplicationProperties() {
        try (InputStream in = ServiceManager.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            applicationProperties.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
