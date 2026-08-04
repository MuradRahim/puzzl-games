package com.zimablue.puzzlgames.controller;

import com.zimablue.puzzlgames.model.Topic;
import com.zimablue.puzzlgames.service.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
        return topicService.getTopics();
    }

    @GetMapping("/topic/{id}")
    public Topic getTopicsById(@PathVariable Long id) {
        return topicService.getTopicsById(id);
    }

    @GetMapping("/topic/get-by-title")
    public Topic getTopicByTitle(@RequestParam("title") String title) {
        log.info("get-by-title {}", title);
        return topicService.getTopicsByTitle(title);
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
