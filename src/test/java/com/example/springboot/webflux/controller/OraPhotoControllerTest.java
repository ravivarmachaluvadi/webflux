package com.example.springboot.webflux.controller;

import com.example.springboot.webflux.config.OracleDBConfig;
import com.example.springboot.webflux.entity.Photo;
import com.example.springboot.webflux.repository.OraPhotoRepository;
import com.example.springboot.webflux.service.OraPhotoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;


//@SpringBootTest
@ExtendWith(SpringExtension.class)
@WebFluxTest(OraPhotoController.class)
@ContextConfiguration(classes = OracleDBConfig.class)
class OraPhotoControllerTest {
/*
    @SpyBean
    OraPhotoRepository oraPhotoRepository;

    @SpyBean
    OraPhotoService oraPhotoService;*/

    @Autowired
    WebTestClient webTestClient;


    @Test
    public void getRDBPhotos_test1(){

        Flux<Photo> photoFlux = webTestClient.get().uri("/rdb/photos")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Photo.class)
                .getResponseBody();

        StepVerifier.create(photoFlux)
                .expectSubscription()
                .expectNextCount(50)
                .verifyComplete();

    }




}