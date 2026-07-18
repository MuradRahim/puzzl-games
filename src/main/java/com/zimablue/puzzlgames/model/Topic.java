package com.zimablue.puzzlgames.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Topic {

    private Long id;
    private String slug;
    private String title;
    private String description;
    private int questionCount;
}
