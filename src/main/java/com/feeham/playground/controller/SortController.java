package com.feeham.playground.controller;

import com.feeham.playground.model.Sort;
import com.feeham.playground.service.SortService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/sort")
@RequiredArgsConstructor
public class SortController {
    private final SortService sortService;
    @GetMapping
    public Flux<Sort> selection(){
        return sortService.selection();
    }
}
