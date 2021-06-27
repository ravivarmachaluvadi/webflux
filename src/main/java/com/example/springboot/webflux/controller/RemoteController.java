package com.example.springboot.webflux.controller;

import com.example.springboot.webflux.service.InvocationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
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
}
