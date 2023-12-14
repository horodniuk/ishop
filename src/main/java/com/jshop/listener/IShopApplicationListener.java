package com.jshop.listener;



import com.jshop.config.Constants;
import com.jshop.service.ProductService;

import com.jshop.util.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;



public class IShopApplicationListener implements ServletContextListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(IShopApplicationListener.class);


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ProductService productService = SpringUtils.getInstance(sce.getServletContext(), ProductService.class);
            sce.getServletContext().setAttribute(Constants.CATEGORY_LIST, productService.listAllCategories());
            sce.getServletContext().setAttribute(Constants.PRODUCER_LIST, productService.listAllProducers());
        } catch (RuntimeException e) {
            LOGGER.error("Web application 'ishop' init failed: "+e.getMessage(), e);
            throw e;
        }
        LOGGER.info("Web application 'ishop' initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("Web application 'ishop' destroyed");
    }
}
