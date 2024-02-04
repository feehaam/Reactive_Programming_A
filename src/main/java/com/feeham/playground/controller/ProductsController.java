package com.feeham.playground.controller;

import com.feeham.playground.service.ProductsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductsController {
    private final ProductsService productsService;

    @GetMapping
    public Flux<Object> getAll(){
        return productsService.getAllProducts();
    }
}
