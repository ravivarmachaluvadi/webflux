package com.example.springboot.webflux.service;

import com.example.springboot.webflux.dto.Photo;
import com.example.springboot.webflux.mongo.repository.MongoPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MongoPhotoService {

    @Autowired
    private MongoPhotoRepository mongoPhotoRepository;

    public Flux<Photo> getPhotos(){

        return mongoPhotoRepository.findAll();
    }

    public Mono<Photo> getPhoto(Integer id){

        return mongoPhotoRepository.findById(id);
    }

    public Flux<Photo> getPhotoInRange(int min,int max){

        return mongoPhotoRepository.findByIdBetween(Range.closed(min,max));
    }

    public Mono<Photo> savePhoto(Mono<Photo> photoMono){

        Mono<Photo> save = photoMono.flatMap(photo -> mongoPhotoRepository.save(photo));
        return save;
    }

    public Mono<Photo> updatePhoto(Mono<Photo> photoMono,int id){

        Mono<Photo> byId = mongoPhotoRepository.findById(id);

        Mono<Photo> objectMono = byId.flatMap(photo -> photoMono.flatMap(photo1 -> {
            photo.setAlbumId(photo1.getAlbumId());
            photo.setThumbnailUrl(photo1.getThumbnailUrl());
            photo.setTitle(photo1.getTitle());
            return byId;
        }));
            return objectMono.flatMap(photo -> mongoPhotoRepository.save(photo));
    }

    public Mono<Void> deletePhoto(int id){

        return mongoPhotoRepository.deleteById(id);
    }

}
