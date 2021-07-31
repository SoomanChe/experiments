package com.sooman_che.config;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.sooman_che.service.SlowTasks;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageProcessor {

    private final static String QUEUE_NAME = "spring-sqs";
    private final QueueMessagingTemplate queueMessagingTemplate;
    private final SlowTasks slowTasks;

    public void send(String data) {
        Message<String> message = MessageBuilder.withPayload(data).build();
        queueMessagingTemplate.send(QUEUE_NAME, message);
    }

    @SqsListener(value = QUEUE_NAME, deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    public void receive(String message, Acknowledgment ack) {
        log.info("Event : {}", message);
        slowTasks.run();
        ack.acknowledge();
    }
}