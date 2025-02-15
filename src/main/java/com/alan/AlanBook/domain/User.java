package com.alan.AlanBook.domain;

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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name", nullable = false, length = 35)
    private String fullName;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "phone_number", nullable = true, length = 10)
    private String phoneNumber;

    @Column(name = "address", nullable = true, length = 100)
    private String address;

    @Column(name = "nation", nullable = true, length = 30)
    private String nation;

    @Column(name = "province", nullable = true, length = 30)
    private String province;

    @Column(name = "district", nullable = true, length = 30)
    private String district;

    @Column(name = "ward", nullable = true, length = 30)
    private String ward;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @JsonBackReference
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Order> orders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Cart> carts;
}
