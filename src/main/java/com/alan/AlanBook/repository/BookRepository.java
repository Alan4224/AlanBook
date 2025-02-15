package com.alan.AlanBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alan.AlanBook.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}
