package com.jshop.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category extends AbstractEntity<Integer> {
    @Id
    private Integer id;
    private String name;
    private String url;
    @Column(name = "product_count")
    private Integer productCount;

    public Category() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    @Override
    public String toString() {
        return String.format("Category [id=%s, name=%s, url=%s, productCount=%s]", getId(), name, url, productCount);
    }
}
