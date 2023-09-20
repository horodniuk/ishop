package com.jshop.listener;


import com.jshop.service.BusinessService;
import com.jshop.service.ServiceManager;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {
    private BusinessService businessService;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        businessService = ServiceManager.getInstance(se.getSession().getServletContext()).getBusinessService();
        businessService.doSomething();
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        businessService.doSomething();
    }
}
