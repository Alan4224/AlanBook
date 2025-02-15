package com.alan.AlanBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alan.AlanBook.domain.BookCategory;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Integer> {

}
