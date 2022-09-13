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
    public ResponseEntity<Offer> getFromWebsiteOfferListByCityNameAndSquareMeterPriceBetween(
            @RequestParam String cityName,
            @RequestParam(required = false) Double startSquareMeterPrice,
            @RequestParam(required = false) Double endSquareMeterPrice) throws IOException {
        log.info("Start to get offers from website for city with name '" + cityName + "' and price between " + startSquareMeterPrice + " and " + endSquareMeterPrice);
        Double startPriceForSquareMeter = startSquareMeterPrice != null ? startSquareMeterPrice : 0.0;
        Double endPriceForSquareMeter = endSquareMeterPrice != null ? endSquareMeterPrice : 12000.0;
        return ResponseEntity.ok(offerService.getOfferByCityName(cityName, startPriceForSquareMeter, endPriceForSquareMeter));
    }

}
