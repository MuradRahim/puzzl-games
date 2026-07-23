package com.zimablue.puzzlgames.service;

import com.zimablue.puzzlgames.model.Topic;
import com.zimablue.puzzlgames.repository.CrudTopicRepository;
import com.zimablue.puzzlgames.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;
    private final CrudTopicRepository topicCrudRepository;

    public void save(String slug, String title, String description, int questionCount) {
        Topic topic = new Topic();
        topic.setSlug(slug);
        topic.setTitle(title);
        topic.setDescription(description);
        topic.setQuestionCount(questionCount);

        topicCrudRepository.createTopic(topic);
    }
}
