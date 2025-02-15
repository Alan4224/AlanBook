package com.alan.AlanBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alan.AlanBook.domain.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer> {
    public Provider findByName(String name);
}
