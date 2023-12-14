package com.jshop.controller.page;


import com.jshop.config.Constants;
import com.jshop.controller.AbstractController;
import com.jshop.entity.Order;
import com.jshop.model.CurrentAccount;
import com.jshop.util.RoutingUtils;
import com.jshop.util.SessionUtils;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/my-orders")
public class MyOrdersController extends AbstractController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CurrentAccount currentAccount = SessionUtils.getCurrentAccount(req);
        List<Order> orders = getOrderService().listMyOrders(currentAccount, 1, Constants.ORDERS_PER_PAGE);
        req.setAttribute("orders", orders);
        int orderCount = getOrderService().countMyOrders(currentAccount);
        req.setAttribute("pageCount", getPageCount(orderCount, Constants.ORDERS_PER_PAGE));
        RoutingUtils.forwardToPage("my-orders.jsp", req, resp);
    }
}
