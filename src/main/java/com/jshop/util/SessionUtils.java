package com.jshop.util;

import com.jshop.model.CurrentAccount;
import com.jshop.model.ShoppingCart;
import com.jshop.config.Constants;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SessionUtils {
    public static ShoppingCart getCurrentShoppingCart(HttpServletRequest req){
        ShoppingCart shoppingCart = (ShoppingCart) req.getSession().getAttribute(Constants.CURRENT_SHOPPING_CART);
        if(shoppingCart == null){
            shoppingCart = new ShoppingCart();
            setCurrentShoppingCart(req, shoppingCart);
        }
        return shoppingCart;
    }

    public static boolean isCurrentShoppingCartCreated(HttpServletRequest req) {
        return req.getSession().getAttribute(Constants.CURRENT_SHOPPING_CART) != null;
    }
    public static void setCurrentShoppingCart(HttpServletRequest req, ShoppingCart shoppingCart) {
        req.getSession().setAttribute(Constants.CURRENT_SHOPPING_CART, shoppingCart);
    }
    public static void clearCurrentShoppingCart(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().removeAttribute(Constants.CURRENT_SHOPPING_CART);
        WebUtils.setCookie(Constants.Cookie.SHOPPING_CART.getName(), null, 0, resp);
    }
    public static Cookie findShoppingCartCookie(HttpServletRequest req){
        return WebUtils.findCookie(req, Constants.Cookie.SHOPPING_CART.getName());
    }
    public static void updateCurrentShoppingCartCookie(String cookieValue, HttpServletResponse resp) {
        WebUtils.setCookie(Constants.Cookie.SHOPPING_CART.getName(), cookieValue,
                Constants.Cookie.SHOPPING_CART.getTtl(), resp);
    }

    public static CurrentAccount getCurrentAccount(HttpServletRequest req) {
        return (CurrentAccount) req.getSession().getAttribute(Constants.CURRENT_ACCOUNT);
    }
    public static void setCurrentAccount(HttpServletRequest req, CurrentAccount currentAccount) {
        req.getSession().setAttribute(Constants.CURRENT_ACCOUNT, currentAccount);
    }
    public static boolean isCurrentAccountCreated(HttpServletRequest req) {
        return getCurrentAccount(req) != null;
    }

    private SessionUtils() {
    }
}
