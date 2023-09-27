package com.jshop.model;

import com.jshop.entity.Product;

public class ShoppingCartItem {
    private Product product;
    private int count;

    public ShoppingCartItem() {
    }

    public ShoppingCartItem(Product product, int count) {
        this.product = product;
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ShoppingCartItem{");
        sb.append("product=").append(product);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
