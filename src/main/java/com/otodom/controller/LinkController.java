package com.otodom.controller;

import com.otodom.service.LinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

}
