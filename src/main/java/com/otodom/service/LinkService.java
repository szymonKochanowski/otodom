package com.otodom.service;

import com.otodom.entity.Link;
import com.otodom.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    public Link addNewLink(Integer offerId, Link newLink) {
        Link link = new Link();
        link.setURL(newLink.getURL());
        link.setOfferId(offerId);
        linkRepository.save(link);
        return link;
    }

}
