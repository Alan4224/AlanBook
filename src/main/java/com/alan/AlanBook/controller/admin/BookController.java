package com.alan.AlanBook.controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alan.AlanBook.service.BookService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/admin/book/crawl")
    public void crawl() {
        bookService.crawlData();
    }

    @GetMapping("/test")
    @ResponseBody
    public List<String> getMethodName() {
        try {
            Document doc = Jsoup
                    .connect(
                            "https://www.vinabook.com/products/ky-thuat-giao-dich-de-kiem-tien-hang-ngay-tren-ttck")
                    .get();
            Elements soldOut = doc.select("div.product-title > span.pro-soldold");
            String price = "";
            if (soldOut.size() != 0)// bán hết
            {
                price = doc.select("div.product-price > span.pro-price").text().replace("₫",
                        "").trim()
                        .replace(",", "");
            } else {
                Elements del = doc.select("div.product-price > del");
                if (!del.text().equals("")) {
                    price = doc.select("div.product-price > del").text().replace("₫", "").trim()
                            .replace(",", "");
                } else {
                    price = doc.select("div.product-price > span.pro-price").text().replace("₫",
                            "").trim()
                            .replace(",", "");
                }
            }
            // price += doc.select("div.product-price > span.pro-price").text();
            // price += soldOut.size();
            List<String> rep2 = new ArrayList<>();
            rep2.add(price);
            return rep2;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
