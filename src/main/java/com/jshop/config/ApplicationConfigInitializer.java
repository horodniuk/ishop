package com.jshop.config;

import com.jshop.servlet.JavaConfigServlet;
import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;

import java.util.Set;

public class ApplicationConfigInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        JavaConfigServlet servlet = new JavaConfigServlet();
        ServletRegistration.Dynamic servletConfig = servletContext.addServlet("JavaConfigServlet", servlet);
        servletConfig.addMapping("/java");
//        servletContext.addListener(new ContextServletListenerImpl3());
    }
}
