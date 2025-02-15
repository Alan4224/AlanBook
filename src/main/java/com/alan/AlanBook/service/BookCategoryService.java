package com.alan.AlanBook.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alan.AlanBook.domain.Book;
import com.alan.AlanBook.domain.BookCategory;
import com.alan.AlanBook.domain.BookType;
import com.alan.AlanBook.repository.BookCategoryRepository;
import com.alan.AlanBook.repository.BookTypeRepository;

@Service
public class BookCategoryService {
    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    @Autowired
    private BookTypeRepository bookTypeRepository;

    public void crawlData() {
        try {
            Document doc = Jsoup.connect("https://www.vinabook.com/").get();
            Elements categories = doc.select("li.has-child.dropdown > a");
            for (Element category : categories) {
                BookCategory bookCategory = new BookCategory();
                bookCategory.setName(category.text());
                bookCategoryRepository.save(bookCategory);
                Elements types = doc.select("li.has-child.dropdown > ul > li > a");
                for (Element type : types) {
                    BookType bookType = new BookType();
                    bookType.setName(type.text());
                    bookType.setBookCategory(bookCategory);
                    bookTypeRepository.save(bookType);
                    // Document typePage = Jsoup.connect(type.attr("href")).get();
                    // Elements bookLinks = typePage.select("div.product-img > a");
                    // for (Element bookLink : bookLinks) {
                    // Document bookPage = Jsoup.connect(bookLink.attr("href")).get();
                    // String name = bookPage.select("div.product-title > h1").text();
                    // String price = bookPage.select("div.product-price > del").text().replace("đ",
                    // "");
                    // Elements soldOut = bookPage.select("div.product-title > span.pro-soldold");
                    // Book book = new Book();
                    // book.setName(name);
                    // book.setPrice(new BigDecimal(price));
                    // if (soldOut.size() != 0)
                    // book.setQuantity(0);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
