package com.jshop.controller.page;


import com.jshop.controller.AbstractController;
import com.jshop.util.RoutingUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/error")
public class ErrorController extends AbstractController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("statusCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        RoutingUtils.forwardToPage("error.jsp", req, resp);
    }
}
