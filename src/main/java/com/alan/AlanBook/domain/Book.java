package com.alan.AlanBook.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<BookImage> bookImages;

    @Column(name = "quantity", nullable = true)
    private Integer quantity;

    @Column(name = "description", nullable = true, length = -1)
    private String introduction;

    @Column(name = "author", nullable = true, length = 70)
    private String author;

    @Column(name = "translator", nullable = true, length = 35)
    private String translator;

    @Column(name = "publisher", nullable = true, length = 30)
    private String publisher;

    @Column(name = "release_year", nullable = true)
    private Integer releaseYear;

    @Column(name = "weight", nullable = true)
    private Float weight;

    @Column(name = "packaging_size", nullable = true, length = 20)
    private String packagingSize;

    @Column(name = "total_page", nullable = true)
    private Integer totalPage;

    @Column(name = "format", nullable = true, length = 15)
    private String format;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "provider_id", referencedColumnName = "id")
    @JsonBackReference
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "book_type_id", referencedColumnName = "id")
    @JsonBackReference
    private BookType bookType;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Cart> carts;
}
