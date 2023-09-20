package com.jshop.filter;


import com.jshop.model.ShoppingCart;
import com.jshop.utils.SessionUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class AutoRestoreShoppingCartFilter implements Filter {
    private static final String SHOPPING_CART_DESERIALIZATION_DONE = "SHOPPING_CART_DESERIALIZATION_DONE";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if(req.getSession().getAttribute(SHOPPING_CART_DESERIALIZATION_DONE) == null){
            if(!SessionUtils.isCurrentShoppingCartCreated(req)){
                Cookie cookie = SessionUtils.findShoppingCartCookie(req);
                if(cookie != null){
                    ShoppingCart shoppingCart = shoppingCartFromString(cookie.getValue());
                    SessionUtils.setCurrentShoppingCart(req, shoppingCart);
                }
            }
            req.getSession().setAttribute(SHOPPING_CART_DESERIALIZATION_DONE, Boolean.TRUE);
        }
        chain.doFilter(req, resp);
    }

    private ShoppingCart shoppingCartFromString(String cookieValue) {
        ShoppingCart shoppingCart = new ShoppingCart();
        String [] items = cookieValue.split("//|");
        for (String item : items) {
            String [] data = item.split("-");
            try{
                int idProduct = Integer.parseInt(data[0]);
                int count = Integer.parseInt(data[1]);
                shoppingCart.addProduct(idProduct, count);
            } catch (RuntimeException e){

            }
        }
        return shoppingCart;
    }

    @Override
    public void destroy() {
    }
}
