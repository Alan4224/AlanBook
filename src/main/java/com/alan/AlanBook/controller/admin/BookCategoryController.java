package com.alan.AlanBook.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.alan.AlanBook.service.BookCategoryService;

@Controller
public class BookCategoryController {
    @Autowired
    private BookCategoryService bookCategoryService;

    @GetMapping("/admin/book-category/crawl")
    public String crawlData() {
        bookCategoryService.crawlData();
        return "admin/book-category/show";
    }
}
