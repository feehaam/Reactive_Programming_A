package com.feeham.playground.service;

import com.feeham.playground.model.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SortService {
    private final int itemCount = 40000;
    private final int sortCount = 10;

    public Flux<Sort> selection() {
        List<Mono<Sort>> sortMonos = new ArrayList<>();
        for (int i = 0; i < sortCount; i++) {
            Sort sort = new Sort();
            sort.setSi(i);
            sortMonos.add(runSelectionSort(sort).subscribeOn(Schedulers.parallel()));
        }
        return Flux.merge(sortMonos);
    }

    private Mono<Sort> runSelectionSort(Sort sort) {
        return Mono.fromCallable(() -> {
            List<Integer> list = getNumbers(sort.getSi());
            preProcess(sort);
            sort.setIterations(sort(list));
            postProcess(sort);
            return sort;
        }).subscribeOn(Schedulers.parallel());
    }

    private void preProcess(Sort sort){
        sort.setStartTime(System.currentTimeMillis());
        print(sort.getSi(), "Sorting started.");
    }

    private long sort(List<Integer> list){
        int index = 0, iterations = 0;
        List<Integer> sorted = new ArrayList<>();
        while (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++, iterations++) {
                if (list.get(i) < list.get(index)) {
                    index = i;
                }
            }
            sorted.add(list.get(index));
            list.remove(index);
            index = 0;
        }
        return iterations;
    }

    private void postProcess(Sort sort){
        sort.setEndTime(System.currentTimeMillis());
        sort.setProcessTime(sort.getEndTime() - sort.getStartTime());
        print(sort.getSi(), "Sorting completed, returning data.");
    }

    private final Random random = new Random();

    private List<Integer> getNumbers(int si) {
        print(si, "Setting unsorted numbers array.");
        List<Integer> unsortedNumbers = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            unsortedNumbers.add(random.nextInt(99999));
        }
        print(si, "Returning unsorted numbers.");
        return unsortedNumbers;
    }

    private void print(int si, String text) {
        System.out.println(si + ". " + text + " @" + Thread.currentThread().getName());
    }
}
