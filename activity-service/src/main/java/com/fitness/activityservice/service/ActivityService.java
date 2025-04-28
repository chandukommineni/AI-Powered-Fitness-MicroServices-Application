package com.fitness.activityservice.service;


import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.exception.ActivityNotFoundException;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ActivityService {
    private ActivityRepository activityRepository;

    public ActivityResponse createActivity(ActivityRequest activityRequest){
        Activity activity=requestMapper(activityRequest);
        return responseMapper(activityRepository.save(activity));
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




}
