package com.zimablue.puzzlgames.controller;

import com.zimablue.puzzlgames.model.Topic;
import com.zimablue.puzzlgames.service.TopicService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TopicController {
    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping("/topic")
    public Topic createTopic(@RequestBody Topic topic) {
        return topicService.createTopic(topic);
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

    @DeleteMapping("/topic/{id}")
    public void deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
    }
}
