package com.example.springboot.webflux.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoCombineTest {

    @Test
    public void combineUsingMerge(){

        Flux<String> flux1= Flux.just("A","B","C");
        Flux<String> flux2= Flux.just("D","E","F");

        Flux<String> mergedFlux = Flux.merge(flux1,flux2).log();

        StepVerifier.create(mergedFlux)
                .expectSubscription()
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();

    }

    @Test
    public void combineUsingMerge_withDelay(){

        //interleaved sequence

        Flux<String> flux1= Flux.just("A","B","C").delayElements(Duration.ofSeconds(1));
        Flux<String> flux2= Flux.just("D","E","F").delayElements(Duration.ofSeconds(1));

        Flux<String> mergedFlux = Flux.merge(flux1,flux2).log();

        StepVerifier.create(mergedFlux)
                .expectSubscription()
                .expectNextCount(6)
                //.expectNext("A","B","C","D","E","F")
                .verifyComplete();

    }

    @Test
    public void combineUsingMerge_withConcat(){

        //interleaved sequence

        Flux<String> flux1= Flux.just("A","B","C").delayElements(Duration.ofSeconds(1));
        Flux<String> flux2= Flux.just("D","E","F").delayElements(Duration.ofSeconds(1));

        Flux<String> mergedFlux = Flux.concat(flux1,flux2).log();

        StepVerifier.create(mergedFlux)
                .expectSubscription()
                //.expectNextCount(6)
                .expectNext("A","B","C","D","E","F")
                .verifyComplete();

    }
}
