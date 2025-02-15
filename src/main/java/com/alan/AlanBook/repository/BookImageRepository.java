package com.alan.AlanBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alan.AlanBook.domain.BookImage;

@Repository
public interface BookImageRepository extends JpaRepository<BookImage, Integer> {

}
