package com.sooman_che.infra;

import com.sooman_che.config.MessageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MessagingController {

    private final MessageProcessor messageProcessor;

    @GetMapping("/send")
    public void sendMessage(@RequestParam String message) {
        messageProcessor.send(message);
    }
}