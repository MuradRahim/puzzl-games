package com.zimablue.puzzlgames.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    private Long id;
    private Long topicId;
    private String questionText;
    private int sortOrder;
}
