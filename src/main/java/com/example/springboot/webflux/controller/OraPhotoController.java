package com.example.springboot.webflux.controller;

import com.example.springboot.webflux.dto.Customer;
import com.example.springboot.webflux.entity.Photo;
import com.example.springboot.webflux.service.OraPhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
public class OraPhotoController {


    @Autowired
    OraPhotoService oraPhotoService;

    @GetMapping("/ora/photos")
    List<Photo> get50Photos(){
        List<Photo>  photoList= oraPhotoService.getPhotosLimited();
        return photoList;
    }

    @GetMapping("/blocking/photos")
    List<Photo> getBlockingPhotos() throws InterruptedException {
        List<Photo>  photoList= oraPhotoService.getPhotosBlocking();
        return photoList;
    }

    @GetMapping(value = "customer/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Customer> getAllCustomersStream() {
        return oraPhotoService.loadAllCustomersStream();
    }

    @GetMapping(value = "/rdb/photos",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<Photo> getRDBPhotos() throws InterruptedException {
        return oraPhotoService.getRDBPhotos();
    }


    @GetMapping("ora/aynsc/photos")
    public List<Photo> getDBPhotosInAsync() throws InterruptedException, ExecutionException {

        List<Photo> photoList =new ArrayList<>(10);

        //async calls
        Instant start = Instant.now();
        List<CompletableFuture<Optional<Photo>>> allFutures = new ArrayList<>();

        for (int i = 1; i <= 5000; i++) {
            allFutures.add(oraPhotoService.getPhotosAysnc(i));
        }
        CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[0])).join();
        for (int i = 0; i < 5000; i++) {
            photoList.add(allFutures.get(i).get().get());
        }
        log.info("Total time: " + Duration.between(start, Instant.now()).getSeconds());
        return photoList;

    }

}
