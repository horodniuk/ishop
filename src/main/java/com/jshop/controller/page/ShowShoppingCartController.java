package com.jshop.controller.page;


import com.jshop.controller.AbstractController;
import com.jshop.util.RoutingUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/shopping-cart")
public class ShowShoppingCartController extends AbstractController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RoutingUtils.forwardToPage("shopping-cart.jsp", req, resp);
    }
}
