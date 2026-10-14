package com.posjetihercegovinu.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private Long id;
    private Long placeId;
    private String authorName;
    private int  rating;
    private String comment;
    private LocalDateTime createdAt;
}
