package com.zimablue.puzzlgames.service;

import com.zimablue.puzzlgames.model.Topic;
import com.zimablue.puzzlgames.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;

    @Transactional
    public Topic createTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    @Transactional(readOnly = true)
    public Topic getTopicsById(Long id) {
        return topicRepository.findById(id)
            .orElse(null);
    }

    @Transactional(readOnly = true)
    public Topic getTopicsByTitle(String title) {
        log.info("Get topic by title {}", title);
        return topicRepository.findByTitle(title)
            .orElse(null);
    }


    @Transactional(readOnly = true)
    public List<Topic> getTopics() {
        return topicRepository.findAll();
    }

    @Transactional
    public Topic updateTopic(Topic topic) {
        if (!topicRepository.existsById(topic.getId())) {
            throw new NoSuchElementException("Тема с id = " + topic.getId() + " не найдена");
        }
        return topicRepository.save(topic);
    }

    @Transactional
    public void deleteTopic(Long id) {
        if (!topicRepository.existsById(id)) {
            throw new NoSuchElementException("Тема с id = " + id + " не найдена");
        }
        topicRepository.deleteById(id);
    }
}
