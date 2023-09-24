package com.jshop.filter;


import com.jshop.config.Constants;
import com.jshop.service.ProductService;
import com.jshop.service.impl.ServiceManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// not working, use annotation @WebFilter
public class CategoryProducerFilter extends AbstractFilter {
    private ProductService productService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       productService = ServiceManager.getInstance(filterConfig.getServletContext()).getProductService();
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setAttribute(Constants.CATEGORY_LIST, productService.listAllCategories());
        request.setAttribute(Constants.PRODUCER_LIST, productService.listAllProducers());
        chain.doFilter(request, response);
    }


}
