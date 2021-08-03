package com.sooman_che;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sooman_che.domain.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SQS;

@Slf4j
@Testcontainers
@SpringBootTest
@RequiredArgsConstructor
@Import({LocalStackTest.SimpleMessageListener.class})
class LocalStackTest {

    @Container
    static LocalStackContainer localStack = new LocalStackContainer(DockerImageName.parse("localstack/localstack:0.12.15"))
            .withServices(SQS)
            .withEnv("DEFAULT_REGION", "ap-northeast-2");
    static CountDownLatch latch;

    final AmazonSQSAsync amazonSQS;
    final QueueMessagingTemplate queueMessagingTemplate;
    final ObjectMapper objectMapper;

    @TestConfiguration
    static class SimpleMessageConfig {

        @Bean
        public AmazonSQSAsync amazonSQS() {
            return AmazonSQSAsyncClientBuilder.standard()
                                              .withCredentials(localStack.getDefaultCredentialsProvider())
                                              .withEndpointConfiguration(localStack.getEndpointConfiguration(SQS))
                                              .build();
        }
    }

    @Slf4j
    @TestComponent
    @RequiredArgsConstructor
    static class SimpleMessageListener {

        @SqsListener(value = "order-event-test-queue", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
        @SneakyThrows
        public void processMessage(@Payload OrderEvent orderEvent, @Headers Map<String, String> headers, Acknowledgment ack) throws JsonProcessingException {
            Thread.sleep(1000);
            log.info("Headers: {}", headers);
            log.info("OrderEvent: {}", orderEvent);
            ack.acknowledge();
            latch.countDown();
        }
    }


    @BeforeAll
    @SneakyThrows
    static void beforeAll() {
        localStack.execInContainer("awslocal", "sqs", "create-queue", "--queue-name", "order-event-test-queue");
    }

    @Test
    @SneakyThrows
    public void testMessageShouldBeUploadedToS3OnceConsumed() {
        latch = new CountDownLatch(1);


        String orderId = UUID.randomUUID().toString();
        OrderEvent orderEvent = getOrderEventBuilder().id(orderId).build();

        objectMapper.writeValueAsString(orderEvent);

        queueMessagingTemplate.convertAndSend("order-event-test-queue", orderEvent);

        given()
                .await()
                .atMost(5, SECONDS)
                .untilAsserted(() -> assertEquals(0, latch.getCount()));

    }

    @Test
    public void slowQueueTest() {
        int targetCount = 100;
        latch = new CountDownLatch(targetCount);

        OrderEvent.OrderEventBuilder orderBuilder = getOrderEventBuilder();

        IntStream.range(0, targetCount).forEach(n -> {
            queueMessagingTemplate.convertAndSend(
                    "order-event-test-queue",
                    orderBuilder.id(String.valueOf(n)).build(),
                    Map.of("MessageDeduplicationId", UUID.randomUUID().toString())
            );
        });

        given()
                .await()
                .pollDelay(Duration.ofSeconds(1))
                .pollInterval(Duration.ofSeconds(1))
                .atMost(Duration.ofSeconds(targetCount / 2))
                .untilAsserted(() -> assertEquals(0, latch.getCount()));

    }

    private OrderEvent.OrderEventBuilder getOrderEventBuilder() {
        return OrderEvent.builder()
                         .product("MacBook")
                         .message("42")
                         .orderedAt(LocalDateTime.now())
                         .expressDelivery(false);
    }

}