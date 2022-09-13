package com.otodom.service;

import com.otodom.entity.Link;
import com.otodom.entity.Offer;
import com.otodom.repository.LinkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class LinkServiceTest {

    @InjectMocks
    LinkService linkService;

    @Mock
    LinkRepository linkRepository;

    @Test
    void addNewLink() {
        //Given
        Integer offerId = preparedOffer().getId();
        Link expectedLink = preparedLink();
        when(linkRepository.save(expectedLink)).thenReturn(expectedLink);
        //When
        Link actualLink = linkService.addNewLink(offerId, expectedLink);
        //Then
        assertEquals(expectedLink.getURL(), actualLink.getURL());
        assertEquals(expectedLink.getOfferId(), actualLink.getOfferId());
    }

    private Link preparedLink() {
        Link link = new Link();
        link.setId(1);
        link.setURL("https://www.otodom.pl/pl/oferty/sprzedaz/mieszkanie/");
        link.setOfferId(1);
        return link;
    }

    private List<Link> preparedLinkList(){
        Link link = preparedLink();
        List<Link> linkList = new ArrayList<>();
        linkList.add(link);
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