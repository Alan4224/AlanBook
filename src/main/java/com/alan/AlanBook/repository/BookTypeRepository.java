package com.alan.AlanBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alan.AlanBook.domain.BookType;

@Repository
public interface BookTypeRepository extends JpaRepository<BookType, Integer> {

}
