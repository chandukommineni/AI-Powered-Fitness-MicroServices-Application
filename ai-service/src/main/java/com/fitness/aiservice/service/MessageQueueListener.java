package com.fitness.aiservice.service;


import com.fitness.aiservice.dto.Activity;
import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageQueueListener {
    private final ActivityRecommendationGeneratorService activityRecommendationGeneratorService;
    private final RecommendationRepository recommendationRepository;
    @RabbitListener(queues = "activity.queue")
    public void listenActivity(Activity activity){
        log.info(activity.toString());
        Recommendation recommendation= activityRecommendationGeneratorService.GetGeneratedRecommendation(activity);
        log.info("Response From AI "+recommendation.toString());
        recommendationRepository.save(recommendation);
        log.info("recommendation saved successfully");

    }
}
