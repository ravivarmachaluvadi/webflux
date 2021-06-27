package com.example.springboot.webflux.service;

import com.example.springboot.webflux.dto.Photo;
import com.example.springboot.webflux.repository.OraPhotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class InvocationHelper {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    OraPhotoRepository oraPhotoRepository;


    @Async
    public CompletableFuture<ResponseEntity> getPhotoDTOAsync(Integer id) throws InterruptedException {
        ResponseEntity<Photo> responseEntity= new ResponseEntity("Custom Response", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        TimeUnit.SECONDS.sleep(1);

        // URI (URL) parameters
        Map<String, Integer> uriParams = new HashMap<>();
        uriParams.put("id",id);

        // Query parameters
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

        String unresolvedUrl = "https://jsonplaceholder.typicode.com/photos/{id}";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(unresolvedUrl).queryParams(queryParams);
        String resolvedUrl = builder.buildAndExpand(uriParams).toUriString();

        log.info(" Thread name : "+Thread.currentThread().getName()+"  "+resolvedUrl );

        //Setting Up Headers
        HttpHeaders httpHeaders = new HttpHeaders();

        List<MediaType> mediaTypeList = new ArrayList<>();

        mediaTypeList.add(MediaType.APPLICATION_JSON);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediaTypeList);
        HttpEntity<String> requestEntity = new HttpEntity<>("", httpHeaders);

        try {
            responseEntity= restTemplate.exchange(resolvedUrl, HttpMethod.GET, requestEntity,Photo.class);

          /*  Photo photo= responseEntity.getBody();
            photoRepository.save(photo);
            */
        } catch (Exception e) {
            log.error("Exception while invoking receive return endpoint for " +e.getMessage());
        }
        return CompletableFuture.completedFuture(responseEntity);
    }


    @Async
    public CompletableFuture<ResponseEntity> offlineAsync(Integer id) throws InterruptedException {
        ResponseEntity<Void> responseEntity= new ResponseEntity(new Photo(1), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

        TimeUnit.SECONDS.sleep(1);

        try {
            log.info(" Thread name : "+Thread.currentThread().getName()+" "+ "https://jsonplaceholder.typicode.com/photos/"+id );
        } catch (Exception e) {
            log.error("Exception while invoking receive return endpoint for " +e.getMessage());
        }
        return CompletableFuture.completedFuture(responseEntity);
    }

    @Async
    public CompletableFuture<ResponseEntity> oraAsync(Integer id) throws InterruptedException {
        ResponseEntity<Void> responseEntity= null;

        TimeUnit.SECONDS.sleep(1);

        Optional<com.example.springboot.webflux.entity.Photo> photo = oraPhotoRepository.findById(id);

        responseEntity=new ResponseEntity(photo.get(), new HttpHeaders(), HttpStatus.OK);

        try {
            log.info(" Thread name : "+Thread.currentThread().getName()+" "+ "https://jsonplaceholder.typicode.com/photos/"+id );
        } catch (Exception e) {
            log.error("Exception while invoking receive return endpoint for " +e.getMessage());
        }
        return CompletableFuture.completedFuture(responseEntity);
    }

}
