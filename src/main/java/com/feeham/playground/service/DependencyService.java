package com.feeham.playground.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class DependencyService {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Mono<?> oneDependent(){
        System.out.println("Got into dependent call method.");
        var response = externalService()
                .doOnSubscribe(subscription -> {
                    System.out.println("Subscribed to external service.");
                })
                .doOnSuccess(s -> {
                    System.out.println("Call succeeded.");
                });
        System.out.println("Returning external service data");
        return response.log();
    }

    public Mono<?> twoIndependent(){
        var response1 = externalService();
        var response2 = externalService();
        return Mono.just("External calls executed.");
    }

    public Mono<?> oneIndependentOneDependent(){
        var response1 = externalService();
        var response2 = externalService();
        return response1;
    }

    private Mono<String> externalService(){
        System.out.println("External service executed.");
        return Mono.just("External data.")
                .delayElement(Duration.ofSeconds(1));
    }
}
