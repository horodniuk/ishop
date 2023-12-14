package com.jshop.entity;




import javax.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem extends AbstractEntity<Long>{
    @Id
    @SequenceGenerator(name = "order_item_seq_generator", sequenceName = "order_item_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_seq_generator")
    private Long id;
    @Column(name = "id_order")
    private Long idOrder;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;
    private Integer count;

    public OrderItem(Long idOrder, Product product, Integer count) {
        super();
        this.idOrder = idOrder;
        this.product = product;
        this.count = count;
    }

    public OrderItem() {
        super();
    }

    public Long getId() {
        return id;
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
