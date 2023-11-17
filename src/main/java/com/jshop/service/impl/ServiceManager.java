package com.jshop.service.impl;

import com.jshop.service.OrderService;
import com.jshop.service.ProductService;
import com.jshop.service.SocialService;
import com.jshop.service.impl.social.FacebookSocialService;
import com.jshop.service.impl.social.GoogleSocialService;
import jakarta.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.dbcp2.BasicDataSource;


public class ServiceManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceManager.class);
    private final Properties applicationProperties = new Properties();
    private final ProductService productService;
    private final OrderService orderService;
    private final SocialService socialService;
    private final BasicDataSource dataSource;

    public static ServiceManager getInstance(ServletContext context) {
        ServiceManager instance = (ServiceManager) context.getAttribute("SERVICE_MANAGER");
        if (instance == null) {
            instance = new ServiceManager(context);
            context.setAttribute("SERVICE_MANAGER", instance);
        }
        return instance;
    }

    private ServiceManager(ServletContext context)  {
            loadApplicationProperties();
            dataSource = createDataSource();
            productService = new ProductServiceImpl(dataSource);
            orderService = new OrderServiceImpl(dataSource, this);
            socialService = new GoogleSocialService(this);
    }

    public void close() {
        try {
            dataSource.close();
        } catch (SQLException e) {
            LOGGER.error("Close datasource failed: "+e.getMessage(), e);
        }
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
        if(value.startsWith("${sysEnv.")) {
            String keyVal = value.replace("${sysEnv.", "").replace("}", "");
            value = System.getenv(keyVal);
        }
        System.out.println(value);
        return value;
    }



    private void loadApplicationProperties(){
        try(InputStream in = ServiceManager.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            applicationProperties.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BasicDataSource createDataSource(){
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
}
