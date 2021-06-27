package com.example.springboot.webflux.controller;

import com.example.springboot.webflux.dto.Photo;
import com.example.springboot.webflux.service.MongoPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class MongoController {

    @Autowired
    MongoPhotoService mongoPhotoService;


    @GetMapping
    public Flux<Photo> getProducts(){
        return mongoPhotoService.getPhotos();
    }

    @GetMapping("/{id}")
    public Mono<Photo> getProduct(@PathVariable Integer id){
        return mongoPhotoService.getPhoto(id);
    }

    @GetMapping("/product-range")
    public Flux<Photo> getProductBetweenRange(@RequestParam("min") Integer min, @RequestParam("max")Integer max){
        return mongoPhotoService.getPhotoInRange(min,max);
    }

    @PostMapping
    public Mono<Photo> saveProduct(@RequestBody Mono<Photo> productDtoMono){
        System.out.println("controller method called ...");
        return mongoPhotoService.savePhoto(productDtoMono);
    }

    @PutMapping("/update/{id}")
    public Mono<Photo> updateProduct(@RequestBody Mono<Photo> productDtoMono,@PathVariable Integer id){
        return mongoPhotoService.updatePhoto(productDtoMono,id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteProduct(@PathVariable Integer id){
        return mongoPhotoService.deletePhoto(id);
    }


}
