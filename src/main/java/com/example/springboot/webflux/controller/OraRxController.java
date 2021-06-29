package com.example.springboot.webflux.controller;

import com.example.springboot.webflux.entity.Photo;
import com.example.springboot.webflux.service.OraRxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RestController
public class OraRxController {

    @Autowired
    OraRxService oraRxService;

    @GetMapping("/ora/rx/photos")
    Flux<Photo> getPhotos(){

        Flux<Photo>  photoList= oraRxService.getPhotos();

        return photoList;
    }
}
