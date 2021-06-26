package com.example.springboot.webflux;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoFluxTest {

    @Test
    public void testMono(){
        Mono<?> stringMono= Mono.just("Ravi")
                .then(Mono.error(new RuntimeException("Exception Occured")))
                .log();
        stringMono.subscribe(System.out::println,(e)-> System.out.println(e.getMessage()));
    }

    @Test
    public void testFlux(){
        Flux<String> stringFlux = Flux.just("Spring", "Boot", "Flux", "Microservice")
                .concatWithValues("AWS")
                .concatWith(Flux.error(new RuntimeException("Flux Exception")))
                .concatWithValues("Cloud")
                .log();
        stringFlux.subscribe(s -> System.out.println(s),(e)-> System.out.println(e.toString()));
    }

}
