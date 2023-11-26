package com.jshop.entity;

import com.jshop.framework.annotation.jdbc.Column;
import com.jshop.framework.annotation.jdbc.Table;

@Table(name="producer")
public class Producer extends AbstractEntity<Integer>{
    private String name;

    @Column("product_count")
    private Integer productCount;

    public Producer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }
}
