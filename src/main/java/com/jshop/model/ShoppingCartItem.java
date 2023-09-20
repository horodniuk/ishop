package com.jshop.model;

public class ShoppingCartItem {
    private int idProduct;
    private int count;

    public ShoppingCartItem() {
    }

    public ShoppingCartItem(int idProduct, int count) {
        this.idProduct = idProduct;
        this.count = count;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ShoppingCartItem{" +
               "idProduct=" + idProduct +
               ", count=" + count +
               '}';
    }
}
