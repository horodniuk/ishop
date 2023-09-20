package com.jshop.listener;


import com.jshop.service.ServiceManager;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ApplicationListener implements ServletContextListener {
    private ServiceManager serviceManager;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        serviceManager = ServiceManager.getInstance(sce.getServletContext());
        serviceManager.getBusinessService().doSomething();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        serviceManager.getBusinessService().doSomething();
        serviceManager.close();
    }
}
