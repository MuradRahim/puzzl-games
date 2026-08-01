package com.zimablue.puzzlgames.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "answers",
    uniqueConstraints = @UniqueConstraint(columnNames = {"question_id", "answer_text"})
)
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "answer_text", nullable = false, columnDefinition = "TEXT")
    private String answerText;

    @Column(name = "is_correct", nullable = false)
    private boolean correct;

    public Long getQuestionId() {
        return question != null ? question.getId() : null;
    }
}
