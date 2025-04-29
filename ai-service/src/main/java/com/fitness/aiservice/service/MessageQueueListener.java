package com.fitness.aiservice.service;


import com.fitness.aiservice.dto.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageQueueListener {

    @RabbitListener(queues = "activity.queue")
    public void listenActivity(Activity activity){
        log.info(activity.toString());
    }
}
