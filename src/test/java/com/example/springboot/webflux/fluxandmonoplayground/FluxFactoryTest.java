package com.example.springboot.webflux.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

public class FluxFactoryTest {

    List<String> names = List.of("adam","anna","jack","jenny");

    @Test
    public void fluxUsingIterable(){

        Flux<String> namesFlux = Flux.fromIterable(names)
                .concatWith(Mono.just("ravi"))
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("adam","anna","jack","jenny","ravi")
                .verifyComplete();

    }

    @Test
    public void fluxUsingArray(){

        String[] names= new String[]{"adam","anna","jack","jenny"};

        Flux<String> namesFlux = Flux.fromArray(names)
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("adam","anna","jack","jenny")
                .verifyComplete();

    }

    @Test
    public void fluxUsingStream(){

        Flux<String> namesFlux = Flux.fromStream(names.stream())
                .log();

        StepVerifier.create(namesFlux)
                .expectNext("adam","anna","jack","jenny")
                .verifyComplete();

    }


    @Test
    public void fluxUsingEmptyAndConcatWith(){

        Flux<Object> namesFlux = Flux.empty().concatWith(Mono.just("ravi"));

        StepVerifier.create(namesFlux)
                .expectNext("ravi")
                .verifyComplete();

    }
}
