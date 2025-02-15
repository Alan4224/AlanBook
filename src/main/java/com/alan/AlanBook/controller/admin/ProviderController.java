package com.alan.AlanBook.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alan.AlanBook.service.ProviderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProviderController {
    @Autowired
    private ProviderService providerService;

    @GetMapping("/admin/provider/crawl")
    public void getMethodName() {
        providerService.crawl();
    }

}
