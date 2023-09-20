package com.jshop.servlet;


import com.jshop.utils.RoutingUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/products")
public class AllProductsController extends AbstractController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       /*List<?> products = getBusinessService().getProducts();
		req.setAttribute("products", products);*/
        RoutingUtils.forwardToPage("products.jsp", req, resp);
    }
}
