package com.sooman.slack_spring_integration;

import com.slack.api.bolt.App;
import com.slack.api.bolt.servlet.SlackAppServlet;
import com.slack.api.bolt.servlet.SlackOAuthAppServlet;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SlackAppController {

    private final static String URL_PREFIX = "/api/slack";

    @WebServlet(URL_PREFIX+"/events")
    public static class SlackEventsController extends SlackAppServlet {
        public SlackEventsController(App app) {
            super(app);
            app.command("/hello-oauth-app", (req, ctx) -> ctx.ack("What's up?"));
        }
    }

    @WebServlet(URL_PREFIX+"/install")
    public static class SlackOAuthInstallController extends SlackOAuthAppServlet {
        public SlackOAuthInstallController(App app) {
            super(app);
        }
    }

    @WebServlet(URL_PREFIX+"/oauth_redirect")
    public static class SlackOAuthRedirectController extends SlackOAuthAppServlet {
        public SlackOAuthRedirectController(App app) {
            super(app);
        }
    }

    @Slf4j
    @Controller
    @RequestMapping(URL_PREFIX)
    public static class SlackWebController{

        @GetMapping("/oauth_completion")
        @SneakyThrows
        public void oAuthComplete(HttpServletResponse response) {
            log.debug("OAuth success");
            response.sendRedirect("https://google.com");
        }

        @GetMapping("/oauth_cancellation")
        @SneakyThrows
        public void oAuthCancellation(HttpServletResponse response) {
            log.debug("OAuth failed");
            response.sendRedirect("https://google.com");
        }
    }
}