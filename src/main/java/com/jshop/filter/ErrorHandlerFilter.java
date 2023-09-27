package com.jshop.filter;


import com.jshop.util.RoutingUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName="ErrorHandlerFilter")
public class ErrorHandlerFilter extends AbstractFilter  {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
       try {
           chain.doFilter(req, resp);
       } catch (Throwable th) {
             String requestUrl = req.getRequestURI();
             LOGGER.error("Request " + requestUrl + " failed: " + th.getMessage(), th);
             resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
             RoutingUtils.forwardToPage("error.jsp", req, resp);

       }
    }
}
