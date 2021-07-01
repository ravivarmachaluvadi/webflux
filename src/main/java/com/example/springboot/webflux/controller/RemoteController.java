package com.example.springboot.webflux.controller;

import com.example.springboot.webflux.dto.Photo;
import com.example.springboot.webflux.service.InvocationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class RemoteController {

    @Autowired
    InvocationHelper invocationHelper;

    @GetMapping("/aynsc/photo")
    public void getPhotoInAsync() throws InterruptedException, ExecutionException {
        //async calls
        Instant start = Instant.now();
        CompletableFuture<ResponseEntity> photoDTOAsync1 = invocationHelper.getPhotoDTOAsync(1);
        CompletableFuture<ResponseEntity> photoDTOAsync2 = invocationHelper.getPhotoDTOAsync(2);
        ResponseEntity responseEntity1 = photoDTOAsync1.get();
        ResponseEntity responseEntity2 = photoDTOAsync2.get();
        System.out.println(responseEntity1.getBody());
        System.out.println(responseEntity2.getBody());
        System.out.println("Total time: " + Duration.between(start, Instant.now()).getSeconds());
    }

    @GetMapping("/aynsc/photos")
    public List<Photo> getPhotosInAsync() throws InterruptedException, ExecutionException {
        List<Photo> photoList =new ArrayList<>(10);
        //async calls
        Instant start = Instant.now();
        List<CompletableFuture<ResponseEntity>> allFutures = new ArrayList<>();
        for (int i = 1; i <=10; i++) {
            allFutures.add(invocationHelper.getPhotoDTOAsync(i));
        }
        CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[0])).join();
        for (int i = 0; i < 10; i++) {
            photoList.add((Photo) allFutures.get(i).get().getBody());
        }
        System.out.println("Total time: " + Duration.between(start, Instant.now()).getSeconds());
        return photoList;
    }

    @GetMapping("/sync/remote/photos")
    public List<Photo> getPhotosRemoteBlocking() throws InterruptedException, ExecutionException {
        List<Photo> photoList =new ArrayList<>(10);

        for (int i = 1; i <=10; i++) {
            photoList.add((Photo) invocationHelper.getPhotoDTORemoteBlocking(i).getBody());
        }
        return photoList;
    }

    @GetMapping("offline/aynsc/photos")
    public List<Photo> getPhotosOfflineInAsync() throws InterruptedException, ExecutionException {
        List<Photo> photoList =new ArrayList<>(5000);
        //async calls
        Instant start = Instant.now();
        List<CompletableFuture<ResponseEntity>> allFutures = new ArrayList<>();
        for (int i = 1; i < 5001; i++) {
            allFutures.add(invocationHelper.offlineAsync(i));
        }
        CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[0])).join();
        for (int i = 0; i < 5000; i++) {
            photoList.add((Photo) allFutures.get(i).get().getBody());
        }
        System.out.println("Total time: " + Duration.between(start, Instant.now()).getSeconds());
        return photoList;
    }

    @GetMapping("ora/async/photos")
    public List<com.example.springboot.webflux.entity.Photo> getPhotosOraInAsync() throws InterruptedException, ExecutionException {
        List<com.example.springboot.webflux.entity.Photo> photoList =new ArrayList<>(5000);
        //async calls
        Instant start = Instant.now();
        List<CompletableFuture<ResponseEntity>> allFutures = new ArrayList<>();
        for (int i = 1; i < 5001; i++) {
            allFutures.add(invocationHelper.oraAsync(i));
        }
        CompletableFuture.allOf(allFutures.toArray(new CompletableFuture[0])).join();

        for (int i = 0; i < 5000; i++) {
            photoList.add((com.example.springboot.webflux.entity.Photo) allFutures.get(i).get().getBody());
        }
        System.out.println("Total time: " + Duration.between(start, Instant.now()).getSeconds());
        return photoList;
    }
}
