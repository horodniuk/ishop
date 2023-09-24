package com.jshop.controller.page;


import com.jshop.config.Constants;
import com.jshop.controller.AbstractController;
import com.jshop.entity.Product;
import com.jshop.util.RoutingUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class AllProductsController extends AbstractController {



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = getProductService().listAllProducts(1, Constants.MAX_PRODUCTS_PER_HTML_PAGE);
        req.setAttribute("products", products);
        RoutingUtils.forwardToPage("products.jsp", req, resp);
    }
}
