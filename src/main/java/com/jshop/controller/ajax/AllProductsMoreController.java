package com.jshop.controller.ajax;


import com.jshop.controller.AbstractController;
import com.jshop.util.RoutingUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/ajax/html/more/products")
public class AllProductsMoreController extends AbstractController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RoutingUtils.forwardToFragment("product-list.jsp", req, resp);
    }
}
