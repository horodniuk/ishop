package com.jshop.controller;

import com.jshop.service.OrderService;
import com.jshop.service.ProductService;
import com.jshop.service.impl.ServiceManager;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

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

}
