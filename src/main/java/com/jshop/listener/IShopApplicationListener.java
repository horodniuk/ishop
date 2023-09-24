package com.jshop.listener;



import com.jshop.config.Constants;
import com.jshop.service.impl.ServiceManager;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class IShopApplicationListener implements ServletContextListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(IShopApplicationListener.class);
    private ServiceManager serviceManager;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            serviceManager = ServiceManager.getInstance(sce.getServletContext());
            sce.getServletContext().setAttribute(Constants.CATEGORY_LIST, serviceManager.getProductService().listAllCategories());
            sce.getServletContext().setAttribute(Constants.PRODUCER_LIST, serviceManager.getProductService().listAllProducers());
        } catch (RuntimeException e) {
            LOGGER.error("Web application 'ishop' init failed: "+e.getMessage(), e);
            e.printStackTrace();
        }
        LOGGER.info("Web application 'ishop' initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        serviceManager.close();
        LOGGER.info("Web application 'ishop' destroyed");
    }
}
