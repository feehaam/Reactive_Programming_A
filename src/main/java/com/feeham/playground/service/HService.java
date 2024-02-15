package com.feeham.playground.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.*;

@Service
public class HService {
    public Mono<Map<Integer, Integer>> analyze() {
        List<String> words = Arrays.asList("A", "B", "C", "D", "E");

        print("Parent", "Started zipping.");

        Mono<Set<Integer>> mono1 = search(words.get(0)).subscribeOn(Schedulers.parallel());
        Mono<Set<Integer>> mono2 = search(words.get(1)).subscribeOn(Schedulers.parallel());

        return Flux.zip(mono1, mono2)
                .map(tuple -> combine(tuple.getT1(), tuple.getT2()))
                .single();
    }


    @SafeVarargs
    private Map<Integer, Integer> combine(Set<Integer> ... results){
        print("Sub", "Combining started.");
        Map<Integer, Integer> map = new HashMap<>();
        for(Set<Integer> result: results){
            for(Integer num: result){
                map.put(num, map.getOrDefault(num, 0) + 1);
            }
        }
        print("Sub", "Combining completed.");
        return map;
    }

    private final Random random = new Random();
    private Mono<Set<Integer>> search(String word){
        print(word, "Preparing search data.");
        Set<Integer> words = new HashSet<>();
        for(int i=0; i<100000; i++){
            words.add(random.nextInt(10000000));
        }
        print(word, "Returning " + words.size() + " results.");
        return Mono.just(words);
    }

    private void print(String tag, String text) {
        System.out.println(tag + ". " + text + " @" + Thread.currentThread().getName());
    }
}
