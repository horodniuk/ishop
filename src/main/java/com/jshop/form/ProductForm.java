package com.jshop.form;

public class ProductForm {
    private Integer idProduct;
    private Integer count;

    public ProductForm() {
    }

    public ProductForm(Integer idProduct, Integer count) {
        this.idProduct = idProduct;
        this.count = count;
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
