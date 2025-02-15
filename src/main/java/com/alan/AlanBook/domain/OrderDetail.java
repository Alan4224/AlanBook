package com.alan.AlanBook.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    @JsonBackReference
    private Book book;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @JsonBackReference
    private Order order;

    @Column(name = "quantity", nullable = true)
    private Integer quantity;
}
