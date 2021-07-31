package com.sooman_che;

import com.sooman_che.domain.EventData;
import com.sooman_che.domain.SampleEvent;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.util.UUID;

@Slf4j
class ControllerIntegrationTest {

//  @SneakyThrows
//  @Test
//  void testCompleteFlow() {
//    amazonSQSAsync.sendMessage(incoming, objectMapper.writeValueAsString(mockEvent()));
//
//    Thread.sleep(3000);
//    await()
//        .pollInterval(Duration.ofMillis(500L))
//        .atMost(Duration.ofSeconds())
//        .untilAsserted(this::assertOutgoingEvent);
//  }
//
//  @SneakyThrows
//  private void assertOutgoingEvent() {
//    var events = amazonSQSAsync.receiveMessage(outgoing).getMessages();
//    assertThat(events).hasSize(1);
//    assertThat(events.get(0)).isNotNull();
//    var sampleEvent = objectMapper.readValue(events.get(0).getBody(), SampleEvent.class);
//    assertThat(events.get(0)).isNotNull();
//    assertThat(sampleEvent.getType()).isEqualTo("incoming-message");
//    assertThat(sampleEvent.getData().getEventType()).isEqualTo(EventData.EventType.PROCESSED);
//  }

  private SampleEvent mockEvent() {
    return SampleEvent.builder()
        .eventId(UUID.randomUUID().toString())
        .eventTime(ZonedDateTime.now())
        .type("incoming-message")
        .version("1.0")
        .data(EventData.builder()
            .eventType(EventData.EventType.CREATED)
            .age(20)
            .name("spring")
            .description("This is user created  incoming event")
            .build())
        .build();
  }
}