package com.sooman_che.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlowTasks {

    @Async
    @SneakyThrows
    public void run(String message) {
        Thread.sleep(1000);
        log.info("Event : {}", message);
    }
}
