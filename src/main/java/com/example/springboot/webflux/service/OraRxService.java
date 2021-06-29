package com.example.springboot.webflux.service;

import com.example.springboot.webflux.entity.Photo;
//import com.example.springboot.webflux.repository.PhotoR2Repository;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class OraRxService {

     /* @Autowired
      PhotoR2Repository photoFluxRepository;*/

      @Autowired
      ConnectionFactory connectionFactory;

      public Flux<Photo> getPhotos() {
            Mono.from(connectionFactory.create())
                    .flatMapMany(connection ->
                            Flux.from(connection.createStatement(
                                    "SELECT * FROM photo_tb")
                                    .execute())
                                    .flatMap(result ->
                                            result.map((row, metadata) -> row.get(0, Photo.class)))
                                    .doOnNext(System.out::println)
                                    .thenMany(connection.close()))
                    .subscribe();
            return null;
      }
}
