package com.fitness.activityservice.service;


import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.exception.ActivityNotFoundException;
import com.fitness.activityservice.exception.InvalidUserException;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final WebClient userWebClient;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public ActivityResponse createActivity(ActivityRequest activityRequest){
        if(!validateUser(activityRequest.getUserId())){
            throw new InvalidUserException("user with Id "+activityRequest.getUserId()+" is Invalid ");
        }
        Activity activity=requestMapper(activityRequest);
        Activity savedActivity=activityRepository.save(activity);

        //send the activity into queue
        try{
         rabbitTemplate.convertAndSend(exchange,routingKey,savedActivity);
        } catch (Exception e) {
            log.info("failed to send activity into queue");
        }
        return responseMapper(savedActivity);
    }

    public ActivityResponse getActivity(String activityId){
       Activity activity=activityRepository.findById(activityId).orElseThrow(()->new ActivityNotFoundException("activity with Id "+activityId+" not found"));
       return responseMapper(activity);
    }



    public List<ActivityResponse> getActivitiesByUserId(String userId){
     List<Activity> activities=activityRepository.findByUserId(userId);
     List<ActivityResponse> activityResponses= new ArrayList<>();

     for(Activity activity:activities){
         activityResponses.add(responseMapper(activity));
     }
     return activityResponses;
    }


    public Activity requestMapper(ActivityRequest activityRequest){

        Activity activity=Activity.builder()
                .type(activityRequest.getType())
                .additionalMetrics(activityRequest.getAdditionalMetrics())
                .caloriesBurned(activityRequest.getCaloriesBurned())
                .duration(activityRequest.getDuration())
                .userId(activityRequest.getUserId())
                .build();
        return activity;
    }



    public ActivityResponse responseMapper(Activity activity){
        ActivityResponse activityResponse=ActivityResponse.builder()
                .id(activity.getId())
                .additionalMetrics(activity.getAdditionalMetrics())
                .caloriesBurned(activity.getCaloriesBurned())
                .type(activity.getType())
                .userId(activity.getUserId())
                .createdAt(activity.getCreatedAt())
                .updatedAt(activity.getUpdatedAt())
                .duration(activity.getDuration())
                .build();
        return activityResponse;
    }

    public Boolean validateUser(String userId){
        boolean isValid =userWebClient
                .get()
                .uri("/api/users/{userId}/validate",userId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        return isValid;

    }





}
