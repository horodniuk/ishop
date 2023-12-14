package com.jshop.controller.page;

import com.jshop.config.Constants;
import com.jshop.controller.AbstractController;
import com.jshop.entity.Product;
import com.jshop.form.SearchForm;
import com.jshop.util.RoutingUtils;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/search")
public class SearchController extends AbstractController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SearchForm searchForm = createSearchForm(req);
        List<Product> products = getProductService().listProductsBySearchForm(searchForm, 1, Constants.MAX_PRODUCTS_PER_HTML_PAGE);
        int totalCount = getProductService().countProductsBySearchForm(searchForm);
        req.setAttribute("products", products);
        req.setAttribute("pageCount", getPageCount(totalCount, Constants.MAX_PRODUCTS_PER_HTML_PAGE));
        req.setAttribute("productCount", totalCount);
        req.setAttribute("searchForm", searchForm);
        RoutingUtils.forwardToPage("search-result.jsp", req, resp);
    }
}
