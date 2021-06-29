package com.example.springboot.webflux.service;

import com.example.springboot.webflux.entity.Photo;
//import com.example.springboot.webflux.repository.PhotoR2Repository;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

@Service
@Slf4j
public class OraRxService {

     /* @Autowired
      PhotoR2Repository photoFluxRepository;*/

      @Autowired
      R2dbcEntityTemplate reactiveTemplate;

      public Flux<Photo> getPhotos() {

           return reactiveTemplate.select(Photo.class)
                   .from("photo_tb")
                    .all()
                   .delayElements(Duration.ofSeconds(1))
                   .log();
                    //.delayElements(Duration.ofSeconds(1));
                    /*.doOnNext(it -> log.info(it))
                    .as(StepVerifier::create)
                    .expectNextCount(1)
                    .verifyComplete();*/
      }
}
