package com.feeham.playground.controller;

import com.feeham.playground.service.HService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/h")
@RequiredArgsConstructor
public class HController {
    private final HService hService;
    @GetMapping
    public Mono<Map<Integer, Integer>> analyze(){
        return hService.analyze();
    }
}
