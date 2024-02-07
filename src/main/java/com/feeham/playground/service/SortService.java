package com.feeham.playground.service;

import com.feeham.playground.models.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SortService {
    private final int ITEM_COUNT = 10000;

    public Flux<Sort> sort() {
        return Flux.range(1, 3)
                .flatMap(item -> getSortObject()
                        .flatMap(this::runSort));
    }

    private Mono<Sort> getSortObject() {
        return Mono.just(Sort.builder()
                        .initTime(System.currentTimeMillis())
                        .totalItems(ITEM_COUNT)
                        .build()
        );
    }

    private Mono<Sort> runSort(Sort sort) {
        sort.setStartTime(System.currentTimeMillis());
        bubbleSort(getUnsortedItems()).flatMap(i -> {
            sort.setThreadName(Thread.currentThread().getName());
            sort.setEndTime(System.currentTimeMillis());
            sort.setTotalTimeTaken(sort.getEndTime() - sort.getStartTime());
            return Mono.empty();
        });

        return Mono.just(sort);
    }

    private Mono<Void> bubbleSort(List<Integer> items) {
        int n = items.size();
        boolean swapped;
        do {
            swapped = false;
            for (int i = 1; i < n; i++) {
                if (items.get(i - 1) > items.get(i)) {
                    // swap items[i-1] and items[i]
                    int temp = items.get(i - 1);
                    items.set(i - 1, items.get(i));
                    items.set(i, temp);
                    swapped = true;
                }
            }
            n--;
        } while (swapped);
        return Mono.empty();
    }

    private List<Integer> getUnsortedItems() {
        Random rand = new Random();
        List<Integer> items = new ArrayList<>();
        for (int i = 0; i < ITEM_COUNT; i++) {
            items.add(rand.nextInt(ITEM_COUNT));
        }
        return items;
    }

}
