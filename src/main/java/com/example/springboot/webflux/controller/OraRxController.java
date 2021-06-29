package com.example.springboot.webflux.controller;

import com.example.springboot.webflux.entity.Photo;
import com.example.springboot.webflux.service.OraRxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RestController
public class OraRxController {

    @Autowired
    OraRxService oraRxService;

    @GetMapping(value = "/ora/rx/photos",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Photo> getPhotos(){

        return  oraRxService.getPhotos();
    }
}
