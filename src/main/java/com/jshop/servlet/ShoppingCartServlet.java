package com.jshop.servlet;


import com.jshop.model.ShoppingCart;
import com.jshop.model.ShoppingCartItem;
import com.jshop.utils.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Random;

@WebServlet("/current-cart")
public class ShoppingCartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cmd = req.getParameter("cmd");
        if ("clear".equals(cmd)) {
            SessionUtils.clearCurrentShoppingCart(req, resp);
        } else if ("invalidate".equals(cmd)) {
            req.getSession().invalidate();
        } else if ("add".equals(cmd)) {
            addProduct(req, resp);
        } else {
            sync(req, resp);
        }
        showShoppingCart(req, resp);
    }

    protected void showShoppingCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*if(SessionUtils.isCurrentShoppingCartCreated(req)) {
            resp.getWriter().println(SessionUtils.getCurrentShoppingCart(req));
        } else {
            resp.getWriter().println("ShoppingCart is null");
        }*/
        req.getRequestDispatcher("/WEB-INF/shopping-cart.jsp").forward(req, resp);
    }

    protected void addProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ShoppingCart shoppingCart = SessionUtils.getCurrentShoppingCart(req);
        Random r = new Random();
        shoppingCart.addProduct(r.nextInt(2), r.nextInt(1)+1);
    }

    protected void sync(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!SessionUtils.isCurrentShoppingCartCreated(req)) {
            Cookie cookie = SessionUtils.findShoppingCartCookie(req);
            if(cookie != null) {
                ShoppingCart shoppingCart = shoppingCartFromString(cookie.getValue());
                SessionUtils.setCurrentShoppingCart(req, shoppingCart);
            }
        } else {
            ShoppingCart shoppingCart = SessionUtils.getCurrentShoppingCart(req);
            String cookieValue = shoppingCartToString(shoppingCart);
            SessionUtils.updateCurrentShoppingCartCookie(cookieValue, resp);
        }
    }

    String shoppingCartToString(ShoppingCart shoppingCart){
        StringBuilder res = new StringBuilder();
        for (ShoppingCartItem shoppingCartItem : shoppingCart.getItems()) {
            res.append(shoppingCartItem.getIdProduct()).append("-").append(shoppingCartItem.getCount()).append("|");
        }
        if (res.length() > 0) {
            res.deleteCharAt(res.length() - 1);
        }
        return res.toString();
    };

    ShoppingCart shoppingCartFromString(String cookieValue) {
        ShoppingCart shoppingCart = new ShoppingCart();
        String[] items = cookieValue.split("\\|");
        for (String item : items) {
            String data[] = item.split("-");
            try {
                int idProduct = Integer.parseInt(data[0]);
                int count = Integer.parseInt(data[1]);
                shoppingCart.addProduct(idProduct, count);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return shoppingCart;
    }
}
