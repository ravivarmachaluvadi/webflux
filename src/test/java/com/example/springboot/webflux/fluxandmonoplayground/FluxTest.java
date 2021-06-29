package com.example.springboot.webflux.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxTest {

    @Test
    public void fluxTestSubscribe(){
      Flux<String> stringFlux =  Flux.just("Spring","Spring Boot","Reactive Spring");
      stringFlux.subscribe(System.out::println);
    }


    @Test
    public void fluxTestSubscribeOnError(){
        Flux<String> stringFlux =  Flux.just("Spring","Spring Boot","Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("new exception")))
                .concatWith(Flux.just("After Error"))
                .log();
        //stringFlux.concatWith(Flux.error(new RuntimeException("new exception"))); //didn't worked
        stringFlux.subscribe(System.out::println,(e)-> System.err.println(e));

    }

    @Test
    public void fluxTestSubscribeOnErrorLog(){
        Flux<String> stringFlux =  Flux.just("Spring","Spring Boot","Reactive Spring")
               /* .concatWith(Flux.error(new RuntimeException("new exception"))) */
               .log();
        stringFlux.subscribe(System.out::println,(e)-> System.err.println(e),()-> System.out.println("Completed"));
    }

    @Test
    public void fluxTestElements_WithOutError(){

        Flux<String> stringFlux =  Flux.just("Spring","Spring Boot","Reactive Spring")
                .log();

        StepVerifier.create(stringFlux)
        .expectNext("Spring")
        .expectNext("Spring Boot")
        .expectNext("Reactive Spring")
        .verifyComplete();

    }


    @Test
    public void fluxTestElements_WithError(){

        Flux<String> stringFlux =  Flux.just("Spring","Spring Boot","Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("new exception")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Spring Boot")
                .expectNext("Reactive Spring")
                //.expectError(RuntimeException.class)
                .expectErrorMessage("new exception")
        .verify();

    }

    @Test
    public void fluxTestElements_WithError1(){

        Flux<String> stringFlux =  Flux.just("Spring","Spring Boot","Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("new exception")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring","Spring Boot","Reactive Spring")
                //.expectError(RuntimeException.class)
                .expectErrorMessage("new exception")
                .verify();

    }

    @Test
    public void fluxTestElementsCount_WithError(){

        Flux<String> stringFlux =  Flux.just("Spring","Spring Boot","Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("new exception")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                //.expectError(RuntimeException.class)
                .expectErrorMessage("new exception")
                .verify();

    }

}
