package com.otodom.service;

import com.otodom.entity.Link;
import com.otodom.entity.Offer;
import com.otodom.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private LinkService linkService;

    public Offer getOfferByCityName(String cityName) throws IOException {
        String content = getWebContentForSpecifiedCity(cityName);
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
            String link = getString(content, i);
            linkObject.setURL(link);
            Link newLink = linkService.addNewLink(offer.getId(), linkObject);
            linkList.add(newLink);
        }

        offer.setLinks(linkList);
        offer.setNumberOfLinks(linkList.size());
        offerRepository.save(offer);
        return offer;
    }

    private String getWebContentForSpecifiedCity(String cityName) throws IOException {
        URL otodomURL = new URL("https://www.otodom.pl/pl/oferty/sprzedaz/mieszkanie/" + cityName);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(otodomURL.openStream()));

        StringBuilder stringBuilder = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            stringBuilder.append(inputLine);
            stringBuilder.append(System.lineSeparator());
        }
        in.close();
        return stringBuilder.toString();
    }

    private static String getString(String content, int i) {
        String substring = content.substring(i);
        String prefixToWebsite = "https://www.otodom.pl/";
        String suffixToWebsite = substring.split(" class")[0];
        String link = prefixToWebsite.concat(suffixToWebsite);
        return link;
    }

}
