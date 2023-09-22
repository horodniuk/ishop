package com.jshop.controller.ajax;


import com.jshop.controller.AbstractController;
import com.jshop.util.RoutingUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/ajax/html/more/products/*")
public class ProductsByCategoryMoreController extends AbstractController {
    private static final int SUBSTRING_INDEX = "/ajax/html/more/products".length();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryUrl = req.getRequestURI().substring(SUBSTRING_INDEX);
        RoutingUtils.forwardToFragment("product-list.jsp", req, resp);
    }
}

















