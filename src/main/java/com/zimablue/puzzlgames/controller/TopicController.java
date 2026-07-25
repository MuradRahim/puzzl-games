package com.zimablue.puzzlgames.controller;

import com.zimablue.puzzlgames.model.Topic;
import com.zimablue.puzzlgames.service.TopicService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TopicController {
    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/topic")
    public List<Topic> getTopics() {
        System.out.println("Привет GetTopics");
        return topicService.getTopics();
    }

    @PutMapping("/topic/update")
    public Topic updateTopic(@RequestBody Topic topic) {
        return topicService.updateTopic(topic);
    }
}
