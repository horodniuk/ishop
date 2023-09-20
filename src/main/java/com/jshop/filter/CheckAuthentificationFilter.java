package com.jshop.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "CheckAuthentificationFilter"/*, urlPatterns = "/*"*/)
public class CheckAuthentificationFilter implements Filter {
    public void init(FilterConfig config) throws ServletException{};

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if(req.getSession().getAttribute("IS_AUTHENTIFICATED") != null){
            filterChain.doFilter(req, response);
        } else {
            String requestUrl = req.getRequestURI();
            req.getSession().setAttribute("REDIRECT_URL_AFTER_SIGNIN", requestUrl);
            ((HttpServletResponse) response).sendRedirect("/sing-in");
        }
    }

    public void destroy(){}
}
