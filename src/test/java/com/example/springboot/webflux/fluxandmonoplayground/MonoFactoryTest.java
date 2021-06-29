package com.example.springboot.webflux.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Supplier;

public class MonoFactoryTest {

    @Test
    public void monoUsingJustOrEmpty(){

        Mono<String> objectMono = Mono.justOrEmpty(null);//Mono.Empty();

        StepVerifier.create(objectMono.log())
                .verifyComplete();

    }

    @Test
    public void monoUsingSupplier(){

        Supplier<String> stringSupplier= ()-> "ravi";

        //Mono<String> objectMono = Mono.fromSupplier(() -> {return "ravi";});//Mono.Empty();
        Mono<String> objectMono = Mono.fromSupplier(stringSupplier);//Mono.Empty();

        StepVerifier.create(objectMono.log())
                .expectNext("ravi")
                .verifyComplete();

    }

    @Test
    public void fluxUsingRange(){

        Flux<Integer> integerFlux = Flux.range(1, 5);

        StepVerifier.create(integerFlux.log())
                .expectNext(1,2,3,4,5)
                .verifyComplete();

    }
}
