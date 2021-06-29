package com.example.springboot.webflux.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static  reactor.core.scheduler.Schedulers.parallel;

public class FluxAndMonoTransformTest {

    List<String> names = List.of("adam","anna","jack","jenny");

    @Test
    public void transformUsingMap(){

        Flux<String> stringFlux = Flux.fromIterable(names)
                .map(s -> s.toUpperCase())
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("ADAM","ANNA","JACK","JENNY")
                .verifyComplete();

    }

    @Test
    public void transformUsingMap_length(){

        Flux<Integer> stringFlux = Flux.fromIterable(names)
                .map(s -> s.length())
                .log();

        StepVerifier.create(stringFlux)
                .expectNext(4,4,4,5)
                .verifyComplete();

    }

    @Test
    public void transformUsingMap_length_repeat(){

        Flux<Integer> stringFlux = Flux.fromIterable(names)
                .map(s -> s.length())
                .repeat(1)
                .log();

        StepVerifier.create(stringFlux)
                .expectNext(4,4,4,5,4,4,4,5)
                .verifyComplete();

    }

    @Test
    public void transformUsing_Flatmap_Mono(){

        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A","B","C","D","E","F"))
                .flatMap(s -> {
                    return getCharMono(s);
                }).log(); //db or external service call that returns flux or mono

        StepVerifier.create(stringFlux)
                .expectNext("a","b","c","d","e","f")
                .verifyComplete();
    }

    private Mono<String> getCharMono(String s) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Mono.just(s.toLowerCase());
    }


    @Test
    public void transformUsing_Flatmap_Flux(){

        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A","B","C","D","E","F"))
                .flatMap(s -> {
                    try {
                        return getCharFlux(s);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).log(); //db or external service call that returns flux or mono

        StepVerifier.create(stringFlux)
                .expectNext("a","newValue","b","newValue","c","newValue","d","newValue","e","newValue","f","newValue")
                .verifyComplete();
    }

    private Flux<String> getCharFlux(String s) throws InterruptedException {

        Thread.sleep(1000);

        return Flux.just(s.toLowerCase(),"newValue");
    }



    @Test
    public void transformUsing_Flatmap_List(){

        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A","B","C","D","E","F"))
                .flatMap(s -> {
                    return Flux.fromIterable(convertToList(s)); // A -> List[a,newValue] , B -> List[b,newValue]
                }).log(); //db or external service call that returns flux or mono

        StepVerifier.create(stringFlux)
                .expectNext("a","newValue","b","newValue","c","newValue","d","newValue","e","newValue","f","newValue")
                .verifyComplete();
    }

    private List<String> convertToList(String s)  {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList(s.toLowerCase(),"newValue");

    }

    @Test
    public void transformUsing_Flatmap_List_parallel(){

        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A","B","C","D","E","F"))
                .window(2) //Flux<Flux<>String> -> (A,B) , (C,D) ,(E,F)
                .flatMap((s) ->
                        s.map(this::convertToList).subscribeOn(parallel())) // Flux<List<String>>
                .flatMap(strings -> Flux.fromIterable(strings)) // Flux<String>
                .log();

        StepVerifier.create(stringFlux)
                //.expectNext("a","newValue","b","newValue","c","newValue","d","newValue","e","newValue","f","newValue")
                .expectNextCount(12)
                .verifyComplete();
    }

    @Test
    public void transformUsing_Flatmap_List_parallel_maintain_order(){

        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A","B","C","D","E","F"))
                .window(2) //Flux<Flux<>String> -> (A,B) , (C,D) ,(E,F)
                /*.concatMap((s) ->
                        s.map(this::convertToList).subscribeOn(parallel()))*/ // Flux<List<String>>
                .flatMapSequential((s) ->
                        s.map(this::convertToList).subscribeOn(parallel()))
                .flatMap(strings -> Flux.fromIterable(strings)) // Flux<String>
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("a","newValue","b","newValue","c","newValue","d","newValue","e","newValue","f","newValue")
                //.expectNextCount(12)
                .verifyComplete();
    }

}
