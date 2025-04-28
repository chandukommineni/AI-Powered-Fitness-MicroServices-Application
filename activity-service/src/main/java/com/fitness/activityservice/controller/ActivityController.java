package com.fitness.activityservice.controller;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.service.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@AllArgsConstructor
public class ActivityController {

    private ActivityService activityService;

    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityResponse> getActivityByID(@PathVariable  String activityId){
        return new ResponseEntity<>(activityService.getActivity(activityId), HttpStatus.FOUND);
    }

    @GetMapping("/user")
    public ResponseEntity<List<ActivityResponse>> getActivitiesofUser(@RequestParam String userId){
        return new ResponseEntity<>(activityService.getActivitiesByUserId(userId),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ActivityResponse> addActivity(@RequestBody ActivityRequest activityRequest){
        return new ResponseEntity<>(activityService.createActivity(activityRequest),HttpStatus.CREATED);
    }


}
