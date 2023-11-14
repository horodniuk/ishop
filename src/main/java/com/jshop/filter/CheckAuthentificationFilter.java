package com.jshop.filter;


import com.jshop.config.Constants;
import com.jshop.util.RoutingUtils;
import com.jshop.util.SessionUtils;
import com.jshop.util.UrlUtils;
import com.jshop.util.WebUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "CheckAuthentificationFilter")
public class CheckAuthentificationFilter extends AbstractFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        if (SessionUtils.isCurrentAccountCreated(req)) {
            chain.doFilter(req, resp);
        } else {
            String requestUrl = WebUtils.getCurrentRequestUrl(req);
            if (UrlUtils.isAjaxUrl(requestUrl)) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().println("401");
            } else {
                req.getSession().setAttribute(Constants.SUCCESS_REDIRECT_URL_AFTER_SIGNIN, requestUrl);
                RoutingUtils.redirect("/sign-in", req, resp);
            }
        }
    }
}
