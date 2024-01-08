package com.feeham.playground.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class LanguageServiceFlux {
    public Flux<String> getLanguages(){
        return Flux.just("Java", "C", "C++", "Ruby", "Python", "C#", "Swift").delayElements(Duration.ofMillis(300));
    }

    public Flux<String> getOS(){
        return Flux.just("Windows", "Linux", "Android", "iOS").delayElements(Duration.ofSeconds(1));
    }

    public Flux<String> fetchDataFromMultipleSources() {
        Flux<String> source1Data = getLanguages();
        Flux<String> source2Data = getOS();

        return Flux.zip(source1Data, source2Data)
                .map(tuple -> {
                    // As the number of items doesn't match, the number of tuple will be
                    // min(collection1 size, collection2 size) by default.
                    // However, this can be I mean should be able to changed.
                    String resultFromSource1 = tuple.getT1();
                    String resultFromSource2 = tuple.getT2();
                    return resultFromSource1 + " and " + resultFromSource2;
                });
    }
}
