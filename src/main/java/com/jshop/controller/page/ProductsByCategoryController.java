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


@WebServlet("/products/*")
public class ProductsByCategoryController extends AbstractController {
    private static final int SUBSTRING_INDEX = "/products".length();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryUrl = req.getRequestURI().substring(SUBSTRING_INDEX);
        List<Product> productList = getProductService().listProductsByCategory(categoryUrl, 1, Constants.MAX_PRODUCTS_PER_HTML_PAGE);
        int totalCount = getProductService().countProductsByCategory(categoryUrl);
        req.setAttribute("pageCount", getPageCount(totalCount, Constants.MAX_PRODUCTS_PER_HTML_PAGE));
        req.setAttribute("products", productList);
        req.setAttribute("selectedCategoryUrl", categoryUrl);
        RoutingUtils.forwardToPage("products.jsp", req, resp);
    }

}
