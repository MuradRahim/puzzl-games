package com.zimablue.puzzlgames.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizSession {

    private String topicSlug;
    private String topicTitle;
    private List<Long> questionIds;
    @Builder.Default
    private int currentIndex = 0;
    @Builder.Default
    private int correctAnswers = 0;

    public boolean hasNextQuestion() {
        return questionIds != null && currentIndex < questionIds.size();
    }

    public Long getCurrentQuestionId() {
        if (!hasNextQuestion()) {
            return null;
        }
        return questionIds.get(currentIndex);
    }

    public int getTotalQuestions() {
        return questionIds == null ? 0 : questionIds.size();
    }

    public static QuizSession start(String topicSlug, String topicTitle, List<Long> questionIds) {
        return QuizSession.builder()
                .topicSlug(topicSlug)
                .topicTitle(topicTitle)
                .questionIds(new ArrayList<>(questionIds))
                .build();
    }
}
