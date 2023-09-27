package com.jshop.model;

import com.jshop.config.Constants;
import com.jshop.entity.Product;
import com.jshop.exception.ValidationException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<Integer, ShoppingCartItem> products = new LinkedHashMap<>();
    private int totalCount = 0;
    private BigDecimal totalCost = BigDecimal.ZERO;


    public void addProduct(Product product, int count) throws ValidationException {
        validateShoppingCartSize(product.getId());
        ShoppingCartItem item = products.get(product.getId());
        if (item == null) {
            validateProductCount(count);
            item = new ShoppingCartItem(product, count);
            products.put(product.getId(), item);
        } else {
            validateProductCount(count + item.getCount());
            item.setCount(item.getCount() + count);
        }
        refreshStatistics();
    }

    public void removeProduct(Integer idProduct, int count) {
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

    public BigDecimal getTotalCost() {
        return totalCost;
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
        totalCost = BigDecimal.ZERO;
        totalCount = 0;
        for (ShoppingCartItem shoppingCartItem : getItems()) {
            totalCount += shoppingCartItem.getCount();
            totalCost = totalCost.add(shoppingCartItem.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(shoppingCartItem.getCount())));
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
        final StringBuilder sb = new StringBuilder("ShoppingCart{");
        sb.append("products=").append(products);
        sb.append(", totalCount=").append(totalCount);
        sb.append(", totalCost=").append(totalCost);
        sb.append('}');
        return sb.toString();
    }
}
