package com.jshop.model;

import com.jshop.config.Constants;
import com.jshop.exception.ValidationException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<Integer, ShoppingCartItem> products = new HashMap<>();
    private int totalCount = 0;


    public void addProduct(int idProduct, int count) throws ValidationException {
        validateShoppingCartSize(idProduct);
        ShoppingCartItem item = products.get(idProduct);
        if (item == null) {
            validateProductCount(count);
            item = new ShoppingCartItem(idProduct, count);
            products.put(idProduct, item);
        } else {
            validateProductCount(count + item.getCount());
            item.setCount(item.getCount() + count);
        }
        refreshStatistics();
    }

    void removeProduct(Integer idProduct, int count) {
        ShoppingCartItem shoppingCartItem = products.get(idProduct);
        if (shoppingCartItem != null) {
            if (shoppingCartItem.getCount() > count) {
                shoppingCartItem.setCount(shoppingCartItem.getCount() - count);
            } else {
                products.remove(idProduct);
            }
            refreshStatistics();
        }
    }

    private void validateShoppingCartSize(int idProduct) {
        if (products.size() > Constants.MAX_PRODUCTS_PER_SHOPPING_CART ||
            (products.size() == Constants.MAX_PRODUCTS_PER_SHOPPING_CART && !products.containsKey(idProduct))) {
            throw new ValidationException("Limit for ShoppingCart size reached: size=" + products.size());
        }
    }

    private void validateProductCount(int count) {
        if (count > Constants.MAX_PRODUCT_COUNT_PER_SHOPPING_CART) {
            throw new ValidationException("Limit for product count reached: count=" + count);
        }
    }

    private void refreshStatistics() {
        totalCount = 0;
        for (ShoppingCartItem shoppingCartItem : getItems()) {
            totalCount += shoppingCartItem.getCount();
        }
    }

    public Collection<ShoppingCartItem> getItems() {
        return products.values();
    }

    public int getTotalCount() {
        return totalCount;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
               "products=" + products +
               ", totalCount=" + totalCount +
               '}';
    }
}
