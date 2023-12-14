package com.jshop.filter;


import com.jshop.config.Constants;
import com.jshop.service.ProductService;
import com.jshop.util.SpringUtils;


import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// not working, use annotation @WebFilter
public class CategoryProducerFilter extends AbstractFilter {
    private ProductService productService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        productService = SpringUtils.getInstance(filterConfig.getServletContext(), ProductService.class);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setAttribute(Constants.CATEGORY_LIST, productService.listAllCategories());
        request.setAttribute(Constants.PRODUCER_LIST, productService.listAllProducers());
        chain.doFilter(request, response);
    }


}
