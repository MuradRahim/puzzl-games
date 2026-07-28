package com.zimablue.puzzlgames.service;

import com.zimablue.puzzlgames.model.Topic;
import com.zimablue.puzzlgames.repository.CrudTopicRepository;
import com.zimablue.puzzlgames.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;
    private final CrudTopicRepository topicCrudRepository;

    public Topic createTopic(Topic topic) {
        return topicCrudRepository.createTopic(topic);
    }

    public Topic getTopicsById(Long id) {
        return topicCrudRepository.getTopicById(id);
    }

    public List<Topic> getTopics() {
        return topicCrudRepository.getAllTopics();
    }

    public Topic updateTopic(Topic topic) {
        return topicCrudRepository.updateTopic(topic);
    }

    public void deleteTopic(Long id) {
        topicCrudRepository.deleteTopicById(id);
    }
}
