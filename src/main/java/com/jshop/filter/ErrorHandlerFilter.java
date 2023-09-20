package com.jshop.filter;


import com.jshop.utils.RoutingUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class ErrorHandlerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
       try {
           chain.doFilter(req, resp);
       } catch (Throwable th) {
           String requestUrl = ((HttpServletRequest)req).getRequestURI();
          // LOGGER.error("Request " + requestUrl + " failed: " + th.getMessage(), th);
             RoutingUtils.forwardToPage("error.jsp", (HttpServletRequest) req, (HttpServletResponse) resp);

       }
    }
}
