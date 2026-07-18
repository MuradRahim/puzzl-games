package com.zimablue.puzzlgames.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizResultView {

    private String topicTitle;
    private String topicSlug;
    private int correctAnswers;
    private int totalQuestions;
    private int scorePercent;
    private String levelTitle;
    private String levelDescription;
}
