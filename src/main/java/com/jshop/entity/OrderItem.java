package com.jshop.entity;

import com.jshop.framework.annotation.jdbc.Child;
import com.jshop.framework.annotation.jdbc.Column;

public class OrderItem extends AbstractEntity<Long>{
    @Column("id_order")
    private Long idOrder;
    @Child(columnName="pid")
    private Product product;
    private Integer count;

    public OrderItem(Product product, int count) {
        super();
        this.product = product;
        this.count = count;
    }

    public OrderItem() {
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return String.format("OrderItem [id=%s, idOrder=%s, product=%s, count=%s]", getId(), idOrder, product, count);
    }
}
