package com.zimablue.puzzlgames.controller;

import com.zimablue.puzzlgames.model.Topic;
import com.zimablue.puzzlgames.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final QuizService quizService;

    public HomeController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Topic> topics = quizService.getAllTopics();
        model.addAttribute("topics", topics);
        return "index";
    }
}
