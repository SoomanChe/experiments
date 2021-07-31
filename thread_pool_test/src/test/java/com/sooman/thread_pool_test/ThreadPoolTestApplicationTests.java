package com.sooman.thread_pool_test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

@SpringBootTest
class ThreadPoolTestApplicationTests {

    @Autowired
    private Tasks tasks;

    @Test
    void contextLoads() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(100);
        IntStream.range(0, 100).forEach(n -> tasks.slowTask(latch,n));
        latch.await();

    }

}
