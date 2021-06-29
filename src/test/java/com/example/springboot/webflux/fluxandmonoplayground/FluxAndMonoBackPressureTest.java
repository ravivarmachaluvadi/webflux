package com.example.springboot.webflux.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoBackPressureTest {

    @Test
    public void backPressureTest(){  // subscriber controlling data emitting from flus

        Flux<Integer> integerFlux = Flux.range(1, 10)
                .log();

        StepVerifier.create(integerFlux)
                .expectSubscription()
                .thenRequest(1)
                .expectNext(1)
                .thenRequest(1)
                .expectNext(2)
                .thenRequest(1)
                .thenCancel()
                .verify();

    }

    @Test
    public void backPressure(){  // subscriber controlling data emitting from flus

        Flux<Integer> integerFlux = Flux.range(1, 10)
                .log();

        integerFlux.subscribe(integer -> System.out.println("Element is : "+integer)
                ,e-> System.err.println("Exception is : "+e)
                ,()-> System.out.println("Done")
                ,(subsciption -> subsciption.request(2)));

    }

    @Test
    public void backPressure_cancel(){  // subscriber controlling data emitting from flus

        Flux<Integer> integerFlux = Flux.range(1, 10)
                .log();

        integerFlux.subscribe(integer -> System.out.println("Element is : "+integer)
                ,e-> System.err.println("Exception is : "+e)
                ,()-> System.out.println("Done")
                ,(subsciption -> subsciption.cancel()));

    }

    @Test
    public void customiseBackPressure(){  // subscriber controlling data emitting from flus

        Flux<Integer> integerFlux = Flux.range(1, 10)
                .log();

        integerFlux.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnNext(Integer value) {
                request(1);
                System.out.println("Value received is : " + value);
                if (value == 4) {
                    cancel();
                }
            }
        });
    }

}
