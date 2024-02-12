package com.feeham.playground.service;

import com.feeham.playground.model.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SortService {
    private final int itemCount = 40000;
    private final int sortCount = 10;

    public Mono<List<Sort>> selection() {
        List<Sort> results = new ArrayList<>();
        for(int i=0; i<sortCount; i++){
            Sort sort = new Sort();
            sort.setSi(i);
            results.add(sort);
        }
//        return Flux.fromIterable(results).flatMap(this::runSelectionSort).collectList();
        Flux<Sort> sortedLists = Flux.fromIterable(results).flatMap(this::runSelectionSort);

        // Use the zip method to combine the results into a single Flux
        Flux<List<Integer>> zippedResult = Flux(sortedLists, (Object[] arrays) -> {
            List<Integer> mergedList = new ArrayList<>();
            for (Object array : arrays) {
                mergedList.addAll((List<Integer>) array);
            }
            return mergedList;
        });
    }

    private Mono<Sort> runSelectionSort(Sort sort){
        List<Integer> list = getNumbers(sort.getSi());
        int index = 0;
        sort.setStartTime(System.currentTimeMillis());
        print(sort.getSi(), "Sorting started.");
        List<Integer> sorted = new ArrayList<>();
        while(!list.isEmpty()){
            for(int i=0; i<list.size(); i++) {
                if(list.get(i) < list.get(index)) {
                    index = i;
                }
            }
            sorted.add(list.get(index));
            list.remove(index);
            index = 0;
        }
        sort.setEndTime(System.currentTimeMillis());
        sort.setProcessTime(sort.getEndTime() - sort.getStartTime());
        print(sort.getSi(), "Sorting completed, returning data.");
        return Mono.just(sort);
    }

    private final Random random = new Random();
    private List<Integer> getNumbers(int si){
        print(si,"Setting unsorted numbers array.");
        List<Integer> unsortedNumbers = new ArrayList<>();
        for(int i=0; i<itemCount; i++) {
            unsortedNumbers.add(random.nextInt(99999));
        }
        print(si, "Returning unsorted numbers.");
        return unsortedNumbers;
    }

    private void print(int si, String text){
        System.out.println(si + ". " + text);
    }
}
