package com.feeham.playground.model;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Sort {
    private Integer si;
    private Long startTime;
    private Long endTime;
    private Long processTime;
    private Long iterations;
}
