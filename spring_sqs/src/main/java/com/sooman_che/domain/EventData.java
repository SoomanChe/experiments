package com.sooman_che.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventData {

  private String name;
  private int age;
  private String description;
  private EventType eventType;

  public enum EventType {
    CREATED, PROCESSED
  }
}