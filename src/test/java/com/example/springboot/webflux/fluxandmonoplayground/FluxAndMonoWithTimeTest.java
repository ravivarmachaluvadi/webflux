package com.example.springboot.webflux.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoWithTimeTest {

    @Test
    public void infiniteSequence() throws InterruptedException {

        Flux<Long> infiniteFlux = Flux.interval(Duration.ofMillis(100))
                .log();// starts frpm 0 --> 1,2,3,......

        infiniteFlux
                .subscribe(aLong -> {
                    System.out.println("Value is : "+aLong);
                });

        Thread.sleep(6000);

    }

    @Test
    public void infiniteSequence_test() throws InterruptedException {

        Flux<Long> finiteFlux = Flux.interval(Duration.ofMillis(100))
                .take(3)
                .log();// starts frpm 0 --> 1,2,3,......

        StepVerifier.create(finiteFlux)
                .expectSubscription()
                .expectNext(0l,1l,2l)
                .verifyComplete();
    }

    @Test
    public void infiniteSequenceMap() throws InterruptedException {

        Flux<Integer> finiteFlux = Flux.interval(Duration.ofMillis(100))
                .map(aLong -> new Integer(aLong.intValue()))
                .take(3)
                .log();// starts frpm 0 --> 1,2,3,......

        StepVerifier.create(finiteFlux)
                .expectSubscription()
                .expectNext(0,1,2)
                .verifyComplete();
    }

    @Test
    public void infiniteSequenceMap_withDelay() throws InterruptedException {

        Flux<Integer> finiteFlux = Flux.interval(Duration.ofMillis(100))
                .delayElements(Duration.ofSeconds(1))
                .map(aLong -> new Integer(aLong.intValue()))
                .take(3)
                .log();// starts frpm 0 --> 1,2,3,......

        StepVerifier.create(finiteFlux)
                .expectSubscription()
                .expectNext(0,1,2)
                .verifyComplete();
    }

}

