package com.jshop.filter;


import com.jshop.service.BusinessService;
import com.jshop.service.ServiceManager;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

@WebFilter("/*")
public class VerificationFilter implements Filter {
    private BusinessService businessService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        businessService = ServiceManager.getInstance(filterConfig.getServletContext()).getBusinessService();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        businessService.doSomething();
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
