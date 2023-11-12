package com.jshop.filter;


import com.jshop.config.Constants;
import com.jshop.util.WebUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName="SetCurrentRequestUrlFilter")
public class SetCurrentRequestUrlFilter extends AbstractFilter {

        @Override
        public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
            req.setAttribute(Constants.CURRENT_REQUEST_URL, WebUtils.getCurrentRequestUrl(req));
            chain.doFilter(req, resp);
        }

}
