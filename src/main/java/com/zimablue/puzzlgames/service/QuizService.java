package com.zimablue.puzzlgames.service;

import com.zimablue.puzzlgames.dto.QuestionView;
import com.zimablue.puzzlgames.dto.QuizResultView;
import com.zimablue.puzzlgames.dto.QuizSession;
import com.zimablue.puzzlgames.model.Answer;
import com.zimablue.puzzlgames.model.Question;
import com.zimablue.puzzlgames.model.Topic;
import com.zimablue.puzzlgames.repository.AnswerRepository;
import com.zimablue.puzzlgames.repository.QuestionRepository;
import com.zimablue.puzzlgames.repository.TopicRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class QuizService {

    private final TopicRepository topicRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public QuizService(TopicRepository topicRepository,
                       QuestionRepository questionRepository,
                       AnswerRepository answerRepository) {
        this.topicRepository = topicRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @Transactional(readOnly = true)
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Topic getTopicBySlug(String slug) {
        return topicRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Тест не найден"));
    }

    @Transactional(readOnly = true)
    public QuizSession startQuiz(String slug) {
        Topic topic = getTopicBySlug(slug);
        List<Long> questionIds = questionRepository.findQuestionIdsByTopicId(topic.getId());
        if (questionIds.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "В этом тесте пока нет вопросов");
        }
        return QuizSession.start(topic.getSlug(), topic.getTitle(), questionIds);
    }

    @Transactional(readOnly = true)
    public QuestionView getCurrentQuestion(QuizSession session) {
        Long questionId = session.getCurrentQuestionId();
        if (questionId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Тест уже завершён");
        }

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Вопрос не найден"));
        List<Answer> answers = answerRepository.findByQuestion_IdOrderById(questionId);

        return QuestionView.builder()
                .questionId(question.getId())
                .questionText(question.getQuestionText())
                .questionNumber(session.getCurrentIndex() + 1)
                .totalQuestions(session.getTotalQuestions())
                .answers(answers)
                .build();
    }

    @Transactional(readOnly = true)
    public boolean submitAnswer(QuizSession session, Long answerId) {
        Long questionId = session.getCurrentQuestionId();
        if (questionId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Тест уже завершён");
        }

        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ответ не найден"));

        if (!answer.getQuestionId().equals(questionId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ответ не относится к текущему вопросу");
        }

        boolean isCorrect = answer.isCorrect();
        if (isCorrect) {
            session.setCorrectAnswers(session.getCorrectAnswers() + 1);
        }
        session.setCurrentIndex(session.getCurrentIndex() + 1);
        return isCorrect;
    }

    public QuizResultView buildResult(QuizSession session) {
        int total = session.getTotalQuestions();
        int correct = session.getCorrectAnswers();
        int percent = total == 0 ? 0 : Math.round((correct * 100f) / total);

        String levelTitle;
        String levelDescription;
        if (percent >= 90) {
            levelTitle = "Отличный результат";
            levelDescription = "Вы уверенно владеете темой и отвечаете почти без ошибок.";
        } else if (percent >= 70) {
            levelTitle = "Хороший результат";
            levelDescription = "База знаний крепкая, но есть несколько тем для повторения.";
        } else if (percent >= 50) {
            levelTitle = "Средний результат";
            levelDescription = "Вы знакомы с темой, однако стоит повторить ключевые понятия.";
        } else {
            levelTitle = "Нужно подтянуть знания";
            levelDescription = "Рекомендуем пройти материалы по теме и попробовать тест снова.";
        }

        return QuizResultView.builder()
                .topicTitle(session.getTopicTitle())
                .topicSlug(session.getTopicSlug())
                .correctAnswers(correct)
                .totalQuestions(total)
                .scorePercent(percent)
                .levelTitle(levelTitle)
                .levelDescription(levelDescription)
                .build();
    }
}
