package com.jshop.servlet;

import com.jshop.service.ServiceManager;
import com.jshop.service.BusinessService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

public abstract class AbstractController extends HttpServlet {
    private BusinessService businessService;

    @Override
    public final void init() throws ServletException {
        businessService = ServiceManager.getInstance(getServletContext()).getBusinessService();
    }

    public BusinessService getBusinessService() {
        return businessService;
    }

}
