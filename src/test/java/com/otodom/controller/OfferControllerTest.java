package com.otodom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otodom.entity.Link;
import com.otodom.entity.Offer;
import com.otodom.service.OfferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private OfferService offerService;

    @Test
    void getFromWebsiteOfferListByCityNameAndSquareMeterPriceBetween() throws Exception {
        //Given
        String cityName = "krakow";
        Double startSquareMeterPrice = 0.0;
        Double endSquareMeterPrice = 12000.0;
        Offer expectedOffer = preparedOffer();
        when(offerService.getOfferByCityName(cityName, startSquareMeterPrice, endSquareMeterPrice)).thenReturn(expectedOffer);
        //When
        MvcResult mvcResult = mockMvc.perform(get("/offer")
                        .param("cityName", cityName)
                        .param("startSquareMeterPrice", String.valueOf(startSquareMeterPrice))
                        .param("endSquareMeterPrice", String.valueOf(endSquareMeterPrice)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        //Then
        Offer actualOffer = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Offer.class);
        assertEquals(expectedOffer, actualOffer);
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