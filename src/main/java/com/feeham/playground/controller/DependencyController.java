package com.feeham.playground.controller;

import com.feeham.playground.service.DependencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("dependency")
@RequiredArgsConstructor
public class DependencyController {

    private final DependencyService dependencyService;

    @GetMapping("/independent")
    public Mono<?> twoIndependent(){
        return dependencyService.twoIndependent();
    }

    @GetMapping("/dependent")
    public Mono<?> oneDependent(){
        return dependencyService.oneDependent();
    }

    @GetMapping("/both")
    public Mono<?> both(){
        return dependencyService.oneIndependentOneDependent();
    }
}
