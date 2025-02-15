package com.alan.AlanBook.service;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alan.AlanBook.domain.Provider;
import com.alan.AlanBook.repository.ProviderRepository;

@Service
public class ProviderService {
    @Autowired
    private ProviderRepository providerRepository;

    public void crawl() {
        try {
            Document doc = Jsoup.connect("https://www.vinabook.com/").get();
            Elements providerLinks = doc.select("div.item.grid__item > a");
            for (Element providerLink : providerLinks) {
                Provider provider = new Provider();
                provider.setImage("https:" + providerLink.getElementsByTag("img").attr("src"));
                Document providerPage = Jsoup.connect(providerLink.attr("href")).get();
                String name = providerPage.select("h1.title").text();
                provider.setName(name);
                providerRepository.save(provider);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
