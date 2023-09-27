package com.jshop.filter;


import com.jshop.form.ProductForm;
import com.jshop.model.ShoppingCart;
import com.jshop.model.ShoppingCartItem;
import com.jshop.service.OrderService;
import com.jshop.service.impl.ServiceManager;
import com.jshop.util.SessionUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName="AutoRestoreShoppingCartFilter")
public class AutoRestoreShoppingCartFilter extends AbstractFilter {
    private static final String SHOPPING_CART_DESERIALIZATION_DONE = "SHOPPING_CART_DESERIALIZATION_DONE";

    private OrderService orderService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        orderService = ServiceManager.getInstance(filterConfig.getServletContext()).getOrderService();
    }

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        if(req.getSession().getAttribute(SHOPPING_CART_DESERIALIZATION_DONE) == null){
            if(!SessionUtils.isCurrentShoppingCartCreated(req)){
                Cookie cookie = SessionUtils.findShoppingCartCookie(req);
                if(cookie != null) {
                    ShoppingCart shoppingCart = orderService.deserializeShoppingCart(cookie.getValue());
                    if(shoppingCart != null) {
                        SessionUtils.setCurrentShoppingCart(req, shoppingCart);
                    }
                }
            }
            req.getSession().setAttribute(SHOPPING_CART_DESERIALIZATION_DONE, Boolean.TRUE);
        }
        chain.doFilter(req, resp);
    }





}
