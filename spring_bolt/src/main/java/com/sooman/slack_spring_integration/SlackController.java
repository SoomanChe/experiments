package com.sooman.slack_spring_integration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SlackController {
    @RequestMapping("/completion")
    public String completion() {
        return "completion.html";
    }
    @RequestMapping("/cancellation")
    public String cancelled() {
        return "cancelled.html";
    }
}
