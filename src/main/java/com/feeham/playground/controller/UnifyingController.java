package com.feeham.playground.controller;

import com.feeham.playground.service.UnifyingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/unify")
@AllArgsConstructor
public class UnifyingController {
    private final UnifyingService unifyingService;
    @GetMapping("/zip")
    public Mono<List<Integer>> getZipped() throws Exception {
        return unifyingService.getZipped();
    }
}
