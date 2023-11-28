package com.jshop.service;

import com.jshop.form.ProductForm;
import com.jshop.model.ShoppingCartItem;

import java.util.Collection;
import java.util.List;

public interface CookieService {

    String createShoppingCartCookie(Collection<ShoppingCartItem> items);

    List<ProductForm> parseShoppingCartCookie(String cookieValue);
}
