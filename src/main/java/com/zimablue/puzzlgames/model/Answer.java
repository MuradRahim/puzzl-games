package com.zimablue.puzzlgames.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

    private Long id;
    private Long questionId;
    private String answerText;
    private boolean correct;
}
