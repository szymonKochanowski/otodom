package com.otodom.service;

import com.otodom.entity.Link;
import com.otodom.entity.Offer;
import com.otodom.repository.OfferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OfferServiceTest {

    @InjectMocks
    OfferService offerService;

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private LinkService linkService;

    @Test
    void getOfferByCityName() throws IOException {
        //Given
        String cityName = "krakow";
        Double startSquareMeterPrice = 6900.0;
        Double endSquareMeterPrice = 7000.0;
        Offer expectedOffer = preparedOffer();
        Link link = preparedLink1();

        when(offerRepository.save(expectedOffer)).thenReturn(expectedOffer);
        when(linkService.addNewLink(expectedOffer.getId(), link)).thenReturn(link);
        when(offerRepository.save(expectedOffer)).thenReturn(expectedOffer);
        //When
        Offer actualOffer = offerService.getOfferByCityName(cityName, startSquareMeterPrice, endSquareMeterPrice);
        //Then
        assertEquals(expectedOffer.getNumberOfLinks(), actualOffer.getNumberOfLinks());
    }

    private Link preparedLink1() {
        Link link = new Link();
        link.setId(1);
        link.setURL("https://www.otodom.pl//pl/oferta/mieszkanie-dwupoziomowe-z-ogrodkiem-krakow-ID4i0LA");
        link.setOfferId(1);
        return link;
    }

    private Link preparedLink2() {
        Link link = new Link();
        link.setId(2);
        link.setURL("https://www.otodom.pl/pl/oferta/mieszkanie-z-ogrodem-i-dwoma-miejscami-ID4dKiK");
        link.setOfferId(1);
        return link;
    }

    private Link preparedLink3() {
        Link link = new Link();
        link.setId(3);
        link.setURL("https://www.otodom.pl/pl/oferty/sprzedaz/mieszkanie/");
        link.setOfferId(1);
        return link;
    }

    private List<Link> preparedLinkList(){
        List<Link> linkList = new ArrayList<>();
        linkList.add(preparedLink1());
        linkList.add(preparedLink2());
        linkList.add(preparedLink3());
        return linkList;
    }

    private Offer preparedOffer() {
        Offer offer = new Offer();
        offer.setId(1);
        offer.setCreatedOn(LocalDateTime.now());
        offer.setLinks(preparedLinkList());
        offer.setNumberOfLinks(preparedLinkList().size());
        return offer;
    }

}