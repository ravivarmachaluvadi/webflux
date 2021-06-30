package com.example.springboot.webflux.service;

import com.example.springboot.webflux.dto.Customer;
import com.example.springboot.webflux.entity.Photo;
import com.example.springboot.webflux.repository.CustomerDao;
import com.example.springboot.webflux.repository.OraPhotoRepository;
//import com.example.springboot.webflux.repository.PhotoFluxRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class OraPhotoService {

    @Autowired
    OraPhotoRepository oraPhotoRepository;

    @Autowired
    CustomerDao customerDao;

    public List<Photo> getPhotosLimited() {

        List<Photo> photos = oraPhotoRepository.findPhotos();

        return photos;
    }

    private static void sleepexecution(int i)  {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Photo> getPhotosBlocking() throws InterruptedException {

        List<Photo> photos = IntStream.rangeClosed(1, 50)
                .peek(OraPhotoService::sleepexecution)
                .mapToObj(value -> oraPhotoRepository.findById(value).get())
                .collect(Collectors.toList());

        return photos;
    }

    public Flux<Photo> getRDBPhotos() throws InterruptedException {

        Flux<Photo> photos = Flux.range(1, 50).log()
                .delayElements(Duration.ofSeconds(1))
                .map(value -> oraPhotoRepository.findById(value).get());

        return photos;
    }

    public Flux<Customer> loadAllCustomersStream() {
        long start = System.currentTimeMillis();
        Flux<Customer> customers = customerDao.getCustomersStream();
        long end = System.currentTimeMillis();
        System.out.println("Total execution time : " + (end - start));
        return customers;
    }

    @Async
    public CompletableFuture<Optional<Photo>> getPhotosAysnc(Integer id) throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        Optional<Photo> byId = oraPhotoRepository.findById(id);
        log.info(" Record fetched with id {} ",byId.get().getId());
        return CompletableFuture.completedFuture(byId);
    }
}
