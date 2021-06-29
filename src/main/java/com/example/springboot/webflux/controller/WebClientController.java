package com.example.springboot.webflux.controller;

import com.example.springboot.webflux.dto.Photo;
import com.example.springboot.webflux.mongo.repository.MongoPhotoRepository;
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
public class WebClientController {

    @Autowired
    WebClient webClient;

    @Autowired
    MongoPhotoRepository mongoPhotoRepository;


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
        //return Flux.fromIterable(integers).delayElements(Duration.ofSeconds(0)).flatMap(integer -> getPhotoMono(integer).map(photo -> photo)).log();
        //return Flux.range(1,200)./*delayElements(Duration.ofSeconds(3)).*/flatMap(integer -> getPhotoMono(integer).map(photo -> photo)).log();
        return Flux.range(1,200)./*delayElements(Duration.ofSeconds(3)).*/flatMap(integer ->
        {
            return getPhotoMono(integer);
        }).log();
    }

    @GetMapping(value = "offline/flux/mono/photos",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Photo> getPhotosOffline() {

        List<Integer> integers = List.of(1, 2, 3, 4, 5);

        //return Flux.range(1,5001).map(i->getPhotoMono(i)).log();
        //return Flux.range(1,5000).flatMap(integer -> getPhotoMono(integer).flatMapMany(Flux::just)).log();
        //return Flux.range(1,5000).flatMap(integer -> getPhotoMono(integer).map(photo -> photo)).log();
        //return Flux.just(5,9,10).delayElements(Duration.ofSeconds(3)).flatMap(integer -> getPhotoMono(integer).map(photo -> photo)).log();
        //return Flux.fromIterable(List.of(5,9,10)).delayElements(Duration.ofSeconds(3)).flatMap(integer -> getPhotoMono(integer).map(photo -> photo)).log();
        //return Flux.fromIterable(integers).delayElements(Duration.ofSeconds(3)).flatMap(integer -> getPhotoMono(integer).map(photo -> photo)).log();
        return Flux.range(1,5000)./*delayElements(Duration.ofSeconds(3)).*/flatMap(integer -> getPhotoMonoOffline(integer).map(photo -> photo)).log();
    }

    @GetMapping(value = "mongo/flux/mono/photos",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Photo> getPhotosMongo() {

        List<Integer> integers = List.of(1, 2, 3, 4, 5);

        //return Flux.range(1,5001).map(i->getPhotoMono(i)).log();
        //return Flux.range(1,5000).flatMap(integer -> getPhotoMono(integer).flatMapMany(Flux::just)).log();
        //return Flux.range(1,5000).flatMap(integer -> getPhotoMono(integer).map(photo -> photo)).log();
        //return Flux.just(5,9,10).delayElements(Duration.ofSeconds(3)).flatMap(integer -> getPhotoMono(integer).map(photo -> photo)).log();
        //return Flux.fromIterable(List.of(5,9,10)).delayElements(Duration.ofSeconds(3)).flatMap(integer -> getPhotoMono(integer).map(photo -> photo)).log();
        //return Flux.fromIterable(integers).delayElements(Duration.ofSeconds(3)).flatMap(integer -> getPhotoMono(integer).map(photo -> photo)).log();
        return Flux.range(1,5000)./*delayElements(Duration.ofSeconds(3)).*/flatMap(integer -> getPhotoMonoMongo(integer).map(photo -> photo)).log();
    }

    private Mono<Photo> getPhotoMonoMongo(Integer integer) {

        Mono<Photo> photoMono = mongoPhotoRepository.findById(integer).delayElement(Duration.ofSeconds(1));
        return  photoMono;
    }

    private Mono<Photo> getPhotoMono(int i) {

        try {
            Thread.sleep(1000); // this seelp behaving differently against  //.delayElement(Duration.ofSeconds(1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return webClient.get()
                .uri("/photos/" + i)
                .retrieve()
                /*.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
                        clientResponse -> Mono.empty())*/
                .bodyToMono(Photo.class);
                //.delayElement(Duration.ofSeconds(1)); this sleep
                //.log();
    }

    private Mono<Photo> getPhotoMonoOffline(int i) {
        return Mono.just(new Photo(i))
                .delayElement(Duration.ofSeconds(1))
                .log();
    }

}

