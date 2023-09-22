package com.jshop.controller;

import com.jshop.service.OrderService;
import com.jshop.service.ProductService;
import com.jshop.service.impl.ServiceManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

public abstract class AbstractController extends HttpServlet {
    private ProductService productService;
    private OrderService orderService;

    @Override
    public final void init() throws ServletException {
        productService = ServiceManager.getInstance(getServletContext()).getProductService();
        orderService = ServiceManager.getInstance(getServletContext()).getOrderService();
    }

    public final ProductService getProductService() {
        return productService;
    }

    public final OrderService getOrderService() {
        return orderService;
    }

}
