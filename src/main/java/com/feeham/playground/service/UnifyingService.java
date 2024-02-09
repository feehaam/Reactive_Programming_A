package com.feeham.playground.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UnifyingService {

    /*
     * In case of zip, calls are async, Data came asynchronously regardless of
     * call time, zipper returned final result after all data came.
     */
    public Mono<List<Integer>> getZipped(){
        return Mono.zip(dataSource1(), dataSource2(), dataSource3(), dataSource4()
        ).flatMap((data -> {
            List<Integer> result = new ArrayList<>(data.getT1());
            result.addAll(data.getT2());
            result.addAll(data.getT3());
            System.out.println("RETURNING ZIPPED DATA.");
            return Mono.just(result);
        })).onErrorResume(throwable -> {
            System.err.println("Error occurred: " + throwable.getMessage());
            return Mono.just(List.of(0)); // Ignore the error and return an empty list
        });

    }

    /*
     * In case of merge, calls are async, Data came asynchronously regardless of
     * call time, returned an empty result initially and updated it whenever a response
     * came.
     */
    public Flux<?> getMerged(){
        return Flux.merge(dataSource1(), dataSource2(), dataSource3(), dataSource4())
                .flatMap((data -> {
                    System.out.println("RETURNING MERGED DATA.");
                    return Mono.just(data);
        })).onErrorResume(throwable -> {
            System.err.println("Error occurred: " + throwable.getMessage());
            return Mono.just(List.of(0));
        });
    }

    private Mono<List<Integer>> dataSource1(){
        System.out.println("Parsing data from source - 1.");
        return Mono.just(List.of(1, 2, 3))
                .delayElement(Duration.ofMillis(1000))
                .flatMap(data -> {
                    System.out.println("Returning data from source - 1.");
                    return Mono.just(data);
                });
    }

    private Mono<List<Integer>> dataSource2(){
        System.out.println("Parsing data from source - 2.");
        return Mono.just(List.of(10, 20, 30))
                .delayElement(Duration.ofMillis(3000))
                .flatMap(data -> {
                    System.out.println("Returning data from source - 2.");
                    return Mono.just(data);
                });
    }

    private Mono<List<Integer>> dataSource3(){
        System.out.println("Parsing data from source - 3.");
        return Mono.just(List.of(100, 200, 300))
                .delayElement(Duration.ofMillis(500))
                .flatMap(data -> {
                    System.out.println("Returning data from source - 3.");
                    return Mono.just(data);
                });
    }

    private Mono<?> dataSource4() {
        System.out.println("Parsing data from source - 4.");
        Random random = new Random();
        if(random.nextInt(100) > 50)
            return Mono.error(new Exception("Data source 4 exception!"))
                    .delayElement(Duration.ofSeconds(4000));
        return Mono.just(new ArrayList<>());
    }
}
