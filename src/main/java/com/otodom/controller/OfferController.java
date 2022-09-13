package com.otodom.controller;

import com.otodom.entity.Offer;
import com.otodom.service.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@Slf4j
@RequestMapping("/offer")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @GetMapping
    public ResponseEntity<Offer> getFromWebsiteOfferListByCityName(@RequestParam String cityName) throws IOException {
        log.info("Start to get offers from website for city with name: " + cityName);
        return ResponseEntity.ok(offerService.getOfferByCityName(cityName));
    }
}
