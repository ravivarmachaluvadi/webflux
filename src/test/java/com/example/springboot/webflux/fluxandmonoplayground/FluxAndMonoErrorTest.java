package com.example.springboot.webflux.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoErrorTest {

    @Test
    public void errorHandling(){

        Flux<String> stringFlux= Flux.just("A","B" , "C")
                .concatWith(Mono.just("D"))
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("E"))
                .onErrorResume((e)->{ // this block will get executed on error
                    System.out.println("Exception is : "+e);
                    return Flux.just("default","default1");
                })
                .log();

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A","B" , "C","D","default","default1")
                //.expectError(RuntimeException.class)
                //.verify();
        .verifyComplete();

    }

    @Test
    public void errorHandling_onErrorReturn(){

        Flux<String> stringFlux= Flux.just("A","B" , "C")
                .concatWith(Mono.just("D"))
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("E"))
                .onErrorReturn("default");

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A","B" , "C","D","default")
                .verifyComplete();

    }

    @Test
    public void errorHandling_onErrorMap(){

        Flux<String> stringFlux= Flux.just("A","B" , "C")
                .concatWith(Mono.just("D"))
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("E"))
                .onErrorMap((e)-> new CustomException(e));

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A","B" , "C","D")
                .expectError(CustomException.class)
                .verify();

    }

    @Test
    public void errorHandling_onErrorMap_withRetry(){

        Flux<String> stringFlux= Flux.just("A","B" , "C")
                .concatWith(Mono.just("D"))
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("E"))
                .onErrorMap((e)-> new CustomException(e))
                .retry(2);

        StepVerifier.create(stringFlux.log())
                .expectSubscription()
                .expectNext("A","B" , "C","D")
                .expectNext("A","B" , "C","D")
                .expectNext("A","B" , "C","D")
                .expectError(CustomException.class)
                .verify();

    }


}
