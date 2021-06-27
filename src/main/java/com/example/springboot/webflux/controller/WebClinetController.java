package com.example.springboot.webflux.controller;

import com.example.springboot.webflux.dto.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@RestController
public class WebClinetController {

    @Autowired
    WebClient webClient;


    @GetMapping(value = "/web/client/photos",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Photo> getPhotosInAsync() {

            return webClient.get()
                    .uri("/photos")
                    .retrieve()
                    .bodyToFlux(Photo.class)
                    .delayElements(Duration.ofSeconds(0)).log();
    }

    @GetMapping("/web/client/photo/{id}")
    public Mono<Photo> getPhotoInAsync(@PathVariable Integer id) {

            return webClient.get()
                    .uri("/photos/" + id)
                    .retrieve()
                    /*.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
                            clientResponse -> Mono.empty())*/
                    .bodyToMono(Photo.class);
        }

    @GetMapping(value = "/flux/mono/photos",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Photo> getPhotos() {

        List<Integer> integers = List.of(1, 2, 3, 4, 5);

        //return Flux.range(1,5001).map(i->getPhotoMono(i)).log();
        //return Flux.range(1,5000).flatMap(integer -> getPhotoMono(integer).flatMapMany(Flux::just)).log();
        //return Flux.range(1,5000).flatMap(integer -> getPhotoMono(integer).map(photo -> photo)).log();
        //return Flux.just(5,9,10).delayElements(Duration.ofSeconds(3)).flatMap(integer -> getPhotoMono(integer).map(photo -> photo)).log();
        //return Flux.fromIterable(List.of(5,9,10)).delayElements(Duration.ofSeconds(3)).flatMap(integer -> getPhotoMono(integer).map(photo -> photo)).log();
        return Flux.fromIterable(integers).delayElements(Duration.ofSeconds(3)).flatMap(integer -> getPhotoMono(integer).map(photo -> photo)).log();
    }

    private Mono<Photo> getPhotoMono(int i) {
        return webClient.get()
                .uri("/photos/" + i)
                .retrieve()
                /*.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
                        clientResponse -> Mono.empty())*/
                .bodyToMono(Photo.class).log();
    }

}

