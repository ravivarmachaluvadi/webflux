package com.example.springboot.webflux.repository;

import com.example.springboot.webflux.dto.Photo;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MongoPhotoRepository extends ReactiveMongoRepository<Photo,Integer> {

    Flux<Photo> findByIdBetween(Range<Integer> closed);
}