package com.jshop.controller.ajax;

import com.jshop.config.Constants;
import com.jshop.controller.AbstractController;
import com.jshop.entity.Order;
import com.jshop.util.RoutingUtils;
import com.jshop.util.SessionUtils;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ajax/html/more/my-orders")
public class MyOrdersMoreController extends AbstractController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orders = getOrderService().listMyOrders(SessionUtils.getCurrentAccount(req), getPage(req), Constants.ORDERS_PER_PAGE);
        req.setAttribute("orders", orders);
        RoutingUtils.forwardToFragment("my-orders-tbody.jsp", req, resp);
    }

}
