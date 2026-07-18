package com.zimablue.puzzlgames.dto;

import com.zimablue.puzzlgames.model.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionView {

    private Long questionId;
    private String questionText;
    private int questionNumber;
    private int totalQuestions;
    private List<Answer> answers;
}
