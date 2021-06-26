package com.example.springboot.webflux.repository;

import com.example.springboot.webflux.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OraPhotoRepository extends JpaRepository<Photo, Integer> {

    //FETCH NEXT 10 ROWS ONLY
    @Query(
            value = "select * from photo_tb FETCH NEXT 50 ROWS ONLY",
            nativeQuery = true)
    List<Photo> findPhotos();

}
