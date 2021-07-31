package com.sooman.thread_pool_test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class Tasks {
    @Async
    public void slowTask(CountDownLatch latch, int n) {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(2));
            System.out.println("Finished slow task" + n);
            latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
