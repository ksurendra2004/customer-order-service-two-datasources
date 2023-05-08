package com.customerorder.entity.order;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "orders_details", schema = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "order_id")
    private Integer id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "order_amount")
    private Integer orderAmount;

    @Column(name = "user_id")
    private Integer userId;
}
