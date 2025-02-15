package com.alan.AlanBook.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alan.AlanBook.domain.Book;
import com.alan.AlanBook.domain.BookImage;
import com.alan.AlanBook.domain.BookType;
import com.alan.AlanBook.domain.Provider;
import com.alan.AlanBook.repository.BookImageRepository;
import com.alan.AlanBook.repository.BookRepository;
import com.alan.AlanBook.repository.BookTypeRepository;
import com.alan.AlanBook.repository.ProviderRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookTypeRepository bookTypeRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private BookImageRepository bookImageRepository;

    public void crawlData() {
        try {
            Document doc = Jsoup.connect("https://www.vinabook.com/").get();
            Elements categories = doc.select("li.has-child.dropdown > a");
            for (Element category : categories) {
                Elements types = doc.select("li.has-child.dropdown > ul > li > a");
                for (Element type : types) {
                    Document typePage = Jsoup.connect("https://www.vinabook.com" + type.attr("href")).get();
                    Elements bookLinks = typePage.select("div.product-img > a");
                    for (Element bookLink : bookLinks) {
                        Document bookPage = Jsoup.connect("https://www.vinabook.com" + bookLink.attr("href")).get();
                        log.info("https://www.vinabook.com" + bookLink.attr("href"));
                        String name = bookPage.select("div.product-title > h1").text();
                        Elements soldOut = bookPage.select("div.product-title > span.pro-soldold");
                        Book book = new Book();
                        book.setName(name);
                        if (soldOut.size() != 0) {
                            String price = bookPage.select("div.product-price > span.pro-price").text().replace("₫", "")
                                    .trim()
                                    .replace(",", "");
                            book.setPrice(new BigDecimal(price));
                            book.setQuantity(0);
                        } else {
                            String price = "";
                            Elements del = bookPage.select("div.product-price > del");
                            if (!del.text().equals("")) {
                                price = bookPage.select("div.product-price > del").text().replace("₫", "").trim()
                                        .replace(",", "");
                            } else {
                                price = bookPage.select("div.product-price > span.pro-price").text().replace("₫",
                                        "").trim()
                                        .replace(",", "");
                            }
                            book.setPrice(new BigDecimal(price));
                            book.setQuantity(100);
                        }
                        Random ran = new Random();
                        Optional<BookType> bType = bookTypeRepository.findById(ran.nextInt(12) + 1);
                        if (bType.isPresent())
                            book.setBookType(bType.get());
                        // mô tả các thứ loại sách query từ db ra so sánh
                        Elements ps = bookPage.select("div.tab-pane.fade.show.active > p");
                        Elements table = bookPage.select("div.tab-pane.fade.show.active > table");
                        String introduction = "";
                        String infor = "";
                        if (table.size() != 0) {
                            for (int i = 0; i < ps.size(); i++) {
                                introduction += ps.get(i).html();
                                introduction += "<br>";
                            }
                            Elements infos = table.select("tbody > tr");
                            for (Element info : infos) {
                                if (info.text().contains("Tác giả")) {
                                    book.setAuthor(info.text().replace("Tác giả ", ""));
                                } else if (info.text().contains("NXB")) {
                                    book.setPublisher(info.text().replace("NXB ", ""));
                                } else if (info.text().contains("Dịch giả")) {
                                    book.setTranslator(info.text().replace("Dịch giả ", ""));
                                } else if (info.text().contains("Năm XB")) {
                                    book.setReleaseYear(Integer.parseInt(info.text().replace("Năm XB ", "")));
                                } else if (info.text().contains("Trọng lượng (gr)")) {
                                    book.setWeight(Float.parseFloat(info.text().replace("Trọng lượng (gr) ", "")));
                                } else if (info.text().contains("Kích Thước Bao Bì")) {
                                    book.setPackagingSize(info.text().replace("Kích Thước Bao Bì ", ""));
                                } else if (info.text().contains("Số trang")) {
                                    book.setTotalPage(Integer.parseInt(info.text().replace("Số trang ", "")));
                                } else if (info.text().contains("Hình thức")) {
                                    book.setFormat(info.text().replace("Hình thức ", ""));
                                } else if (info.text().contains("Tên Nhà Cung Cấp")) {
                                    Provider pro = providerRepository
                                            .findByName(info.text().replace("Tên Nhà Cung Cấp ", ""));
                                    book.setProvider(pro);
                                }
                            }
                        } else {
                            int breakPoint = 0;
                            for (int i = 0; i < ps.size(); i++) {
                                if (ps.get(i).text().contains("Thông tin chi tiết")) {
                                    breakPoint = i;
                                    break;
                                }
                                introduction += ps.get(i).html();
                                introduction += "<br>";
                            }
                            while (breakPoint < ps.size() - 1) {
                                infor += ps.get(breakPoint).html();
                                breakPoint++;
                            }
                            String[] listInfo = infor.split("<br>\n");
                            for (int i = 2; i < listInfo.length; i++) {
                                String[] a = listInfo[i].split("&nbsp;&nbsp; &nbsp;");
                                if (a[0].equalsIgnoreCase("NXB")) {
                                    book.setPublisher(a[1]);
                                } else if (a[0].equalsIgnoreCase("tác giả")) {
                                    book.setAuthor(a[1]);
                                } else if (a[0].equalsIgnoreCase("Dịch giả")) {
                                    book.setTranslator(a[1]);
                                } else if (a[0].equalsIgnoreCase("Năm XB") || a[0].equalsIgnoreCase("Năm xuất bản")) {
                                    book.setReleaseYear(Integer.parseInt(a[1]));
                                } else if (a[0].equalsIgnoreCase("Trọng lượng (gr)")) {
                                    book.setWeight(Float.parseFloat(a[1]));
                                } else if (a[0].equalsIgnoreCase("Kích Thước Bao Bì")) {
                                    book.setPackagingSize(a[1]);
                                } else if (a[0].equalsIgnoreCase("Số trang")) {
                                    book.setTotalPage(Integer.parseInt(a[1]));
                                } else if (a[0].equalsIgnoreCase("Hình thức")) {
                                    book.setFormat(a[1]);
                                } else if (a[0].equalsIgnoreCase("Tên Nhà Cung Cấp")) {
                                    Provider pro = providerRepository.findByName(a[0]);
                                    book.setProvider(pro);
                                }
                            }

                        }
                        book.setIntroduction(introduction);
                        bookRepository.save(book);
                        Elements images = bookPage.select("ul#sliderproduct > li > a > img");
                        for (Element image : images) {
                            BookImage bookImage = new BookImage();
                            bookImage.setPath("https:" + image.attr("src"));
                            bookImage.setBook(book);
                            bookImageRepository.save(bookImage);
                        }
                    }
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
