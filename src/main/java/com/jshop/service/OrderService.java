package com.jshop.service;

import com.jshop.entity.Order;
import com.jshop.form.ProductForm;
import com.jshop.model.CurrentAccount;
import com.jshop.model.ShoppingCart;
import com.jshop.model.SocialAccount;

import java.util.List;

public interface OrderService {
    void addProductToShoppingCart(ProductForm productForm, ShoppingCart shoppingCart);

    void removeProductFromShoppingCart(ProductForm form, ShoppingCart shoppingCart);

    String serializeShoppingCart(ShoppingCart shoppingCart);

    ShoppingCart deserializeShoppingCart(String string);

    CurrentAccount authentificate(SocialAccount socialAccount);

    long makeOrder (ShoppingCart shoppingCart, CurrentAccount currentAccount);

    Order findOrderById(long id, CurrentAccount currentAccount);

    List<Order> listMyOrders(CurrentAccount currentAccount, int page, int limit);

    int countMyOrders(CurrentAccount currentAccount);

}
