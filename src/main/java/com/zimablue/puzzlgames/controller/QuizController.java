package com.zimablue.puzzlgames.controller;

import com.zimablue.puzzlgames.dto.QuestionView;
import com.zimablue.puzzlgames.dto.QuizResultView;
import com.zimablue.puzzlgames.dto.QuizSession;
import com.zimablue.puzzlgames.model.Topic;
import com.zimablue.puzzlgames.service.QuizService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuizController {

    private static final String SESSION_KEY = "quizSession";

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/tests/{slug}")
    public String topicPage(@PathVariable String slug, Model model) {
        Topic topic = quizService.getTopicBySlug(slug);
        model.addAttribute("topic", topic);
        return "topic";
    }

    @PostMapping("/tests/{slug}/start")
    public String startQuiz(@PathVariable String slug, HttpSession session) {
        QuizSession quizSession = quizService.startQuiz(slug);
        session.setAttribute(SESSION_KEY, quizSession);
        return "redirect:/tests/" + slug + "/question";
    }

    @GetMapping("/tests/{slug}/question")
    public String questionPage(@PathVariable String slug, HttpSession session, Model model) {
        QuizSession quizSession = getSessionOrRedirect(slug, session);
        if (quizSession == null) {
            return "redirect:/tests/" + slug;
        }
        if (!quizSession.hasNextQuestion()) {
            return "redirect:/tests/" + slug + "/result";
        }

        QuestionView question = quizService.getCurrentQuestion(quizSession);
        model.addAttribute("topicSlug", slug);
        model.addAttribute("question", question);
        return "question";
    }

    @PostMapping("/tests/{slug}/answer")
    public String submitAnswer(@PathVariable String slug,
                               @RequestParam Long answerId,
                               HttpSession session,
                               Model model) {
        QuizSession quizSession = getSessionOrRedirect(slug, session);
        if (quizSession == null) {
            return "redirect:/tests/" + slug;
        }

        boolean isCorrect = quizService.submitAnswer(quizSession, answerId);
        session.setAttribute(SESSION_KEY, quizSession);

        if (quizSession.hasNextQuestion()) {
            model.addAttribute("isCorrect", isCorrect);
            return "redirect:/tests/" + slug + "/question";
        }
        return "redirect:/tests/" + slug + "/result";
    }

    @GetMapping("/tests/{slug}/result")
    public String resultPage(@PathVariable String slug, HttpSession session, Model model) {
        QuizSession quizSession = getSessionOrRedirect(slug, session);
        if (quizSession == null) {
            return "redirect:/tests/" + slug;
        }
        if (quizSession.hasNextQuestion()) {
            return "redirect:/tests/" + slug + "/question";
        }

        QuizResultView result = quizService.buildResult(quizSession);
        model.addAttribute("result", result);
        session.removeAttribute(SESSION_KEY);
        return "result";
    }

    private QuizSession getSessionOrRedirect(String slug, HttpSession session) {
        Object value = session.getAttribute(SESSION_KEY);
        if (!(value instanceof QuizSession quizSession)) {
            return null;
        }
        if (!slug.equals(quizSession.getTopicSlug())) {
            return null;
        }
        return quizSession;
    }
}
