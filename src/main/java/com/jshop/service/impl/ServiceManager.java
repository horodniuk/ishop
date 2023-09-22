package com.jshop.service.impl;

import com.jshop.service.OrderService;
import com.jshop.service.ProductService;
import jakarta.servlet.ServletContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServiceManager {
    private final Properties applicationProperties = new Properties();
    private final ProductService productService;
    private final OrderService orderService;

    public static ServiceManager getInstance(ServletContext context) {
        ServiceManager instance = (ServiceManager) context.getAttribute("SERVICE_MANAGER");
        if (instance == null) {
            instance = new ServiceManager(context);
            context.setAttribute("SERVICE_MANAGER", instance);
        }
        return instance;
    }

    public void close() {}

    public ProductService getProductService() {
        return productService;
    }
    public OrderService getOrderService() {
        return orderService;
    }

    public String getApplicationProperty(String key) {
        return applicationProperties.getProperty(key);
    }

    private ServiceManager(ServletContext context) {
        loadApplicationProperties();
        productService = new ProductServiceImpl();
        orderService = new OrderServiceImpl();
    }

    private void loadApplicationProperties(){
        try(InputStream in = ServiceManager.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            applicationProperties.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
