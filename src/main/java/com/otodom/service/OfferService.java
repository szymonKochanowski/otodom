package com.otodom.service;

import com.otodom.entity.Link;
import com.otodom.entity.Offer;
import com.otodom.repository.OfferRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private LinkService linkService;

    public Offer getOfferByCityName(String cityName, Double startPriceForSquareMeter, Double endPriceForSquareMeter) throws IOException {
        long start = System.currentTimeMillis();

        String content = getWebContentForSpecifiedCityAndPriceForSquareMeter(cityName, startPriceForSquareMeter, endPriceForSquareMeter);
        List<Link> linkList = new ArrayList<>();
        Offer offer = new Offer();
        offer.setCreatedOn(LocalDateTime.now());
        offerRepository.save(offer);
        Link linkObject = new Link();

        for (int i = 0; i < content.length(); i++) {
            i = content.indexOf("/pl/oferta/", i);
            if (i < 0) {
                break;
            }
            String link = getLink(content, i);
            linkObject.setURL(link);
            Link newLink = linkService.addNewLink(offer.getId(), linkObject);
            linkList.add(newLink);
        }

        offer.setLinks(linkList);
        offer.setNumberOfLinks(linkList.size());
        offerRepository.save(offer);

        long end = System.currentTimeMillis();
        System.out.println("Time needed to perform get is : " + (end - start) + " milliseconds.");

        return offer;
    }

    private String getWebContentForSpecifiedCityAndPriceForSquareMeter(String cityName, Double startPriceForSquareMeter, Double endPriceForSquareMeter) throws IOException {
        String linkToAllOffer = "https://www.otodom.pl/pl/oferty/sprzedaz/mieszkanie/";
        String distance = "?distanceRadius=0";
        String startPriceURLRestriction = "&pricePerMeterMin=";
        String endPriceURLRestriction = "&pricePerMeterMax=";
        String offersLimit = "&limit=100";
        URL otodomURL = new URL( linkToAllOffer + cityName + distance + startPriceURLRestriction + startPriceForSquareMeter + endPriceURLRestriction + endPriceForSquareMeter + offersLimit);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(otodomURL.openStream()));

        StringBuilder stringBuilder = new StringBuilder();
        String inputLine;
        try {
            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
                stringBuilder.append(System.lineSeparator());
            }
        } catch (Exception e) {
            log.error("Bad URL or content of page is empty!");
        } finally {
            in.close();
        }
        return stringBuilder.toString();
    }

    private static String getLink(String content, int i) {
        String substring = content.substring(i);
        String prefixToWebsite = "https://www.otodom.pl/";
        String suffixToWebsite = substring.split(" class")[0];
        return prefixToWebsite.concat(suffixToWebsite);
    }

}
