package com.feeham.playground.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class DependencyService {

    private final WebClient webClient;
    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    public DependencyService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://dummyjson.com").build();
    }

    public Mono<?> oneDependent(){
        System.out.println("Got into dependent call method.");
        var response = externalService(1)
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
        var response1 = externalService(1);
        var response2 = externalService(2);
        return Mono.just("External calls executed.");
    }

    public Mono<?> oneIndependentOneDependent(){
        var response1 = externalService(1);
        var response2 = externalService(2);
        return response1;
    }

    private Mono<Object> externalService(Integer productId){
        System.out.println("Fetching product: " + productId + ". " + Thread.currentThread().getName());
        long time = System.currentTimeMillis();
        return webClient.get()
                .uri("/products/{productId}", productId)
                .retrieve()
                .bodyToMono(Object.class)
                .flatMap(result -> {
                    System.out.println(result);
                    System.out.println("Started at " + time + " ended at " + System.currentTimeMillis()
                            + " | Diff: " + (System.currentTimeMillis() - time)
                            + " --> Thread: " + Thread.currentThread().getName());
                    return Mono.just(result);
                });
    }
}
