package com.feeham.playground.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder @Getter @Setter
public class Sort {
    private Long initTime;
    private Integer totalItems;
    private Long startTime;
    private Long endTime;
    private Long totalTimeTaken;
    private String threadName;

    public Long getStartTime(){
        return getShortenTime(startTime, 4);
    }

    public Long getEndTime(){
        return getShortenTime(endTime, 4);
    }

    private Long getShortenTime(Long number, Integer keepLast){
        String numberAsString = String.valueOf(startTime);
        return Long.parseLong(numberAsString.substring(Math.max(numberAsString.length() - keepLast, 0)));
    }
}
