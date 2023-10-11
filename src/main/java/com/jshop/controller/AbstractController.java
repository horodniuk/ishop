package com.jshop.controller;

import com.jshop.form.ProductForm;
import com.jshop.form.SearchForm;
import com.jshop.service.OrderService;
import com.jshop.service.ProductService;
import com.jshop.service.SocialService;
import com.jshop.service.impl.ServiceManager;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractController extends HttpServlet {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private ProductService productService;
    private OrderService orderService;
    private SocialService socialService;

    @Override
    public final void init() throws ServletException {
        productService = ServiceManager.getInstance(getServletContext()).getProductService();
        orderService = ServiceManager.getInstance(getServletContext()).getOrderService();
        socialService = ServiceManager.getInstance(getServletContext()).getSocialService();
    }

    public final ProductService getProductService() {
        return productService;
    }

    public final OrderService getOrderService() {
        return orderService;
    }

    public SocialService getSocialService() {
        return socialService;
    }

    public final int getPageCount(int totalCount, int itemsPerPage){
        int res = totalCount / itemsPerPage;
        if(res * itemsPerPage !=totalCount){
            res++;
        }
        return res;
    }

    public final int getPage(HttpServletRequest request){
        try {
           return Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
          return 1;
        }
    }

    public final SearchForm createSearchForm(HttpServletRequest request){
      return new SearchForm(request.getParameter("query"),
                            request.getParameterValues("category"),
                            request.getParameterValues("producer")
      );
    }

    public final ProductForm createProductForm(HttpServletRequest request){
        return new ProductForm(
                Integer.parseInt(request.getParameter("idProduct")),
                Integer.parseInt(request.getParameter("count")));
    }

}
