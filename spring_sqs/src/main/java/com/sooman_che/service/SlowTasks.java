package com.sooman_che.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlowTasks {

    @Async
    @SneakyThrows
    public void run() {
        Thread.sleep(1000);
    }
}
