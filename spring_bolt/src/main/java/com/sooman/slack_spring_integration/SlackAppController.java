package com.sooman.slack_spring_integration;

import com.slack.api.bolt.App;
import com.slack.api.bolt.servlet.SlackAppServlet;
import com.slack.api.bolt.servlet.SlackOAuthAppServlet;

import javax.servlet.annotation.WebServlet;

public class SlackAppController {
    @WebServlet("/slack/events")
    public static class SlackEventsController extends SlackAppServlet {
        public SlackEventsController(App app) {
            super(app);
        }
    }

    @WebServlet("/slack/install")
    public static class SlackOAuthInstallController extends SlackOAuthAppServlet {
        public SlackOAuthInstallController(App app) {
            super(app);
        }
    }

    @WebServlet("/slack/oauth_redirect")
    public static class SlackOAuthRedirectController extends SlackOAuthAppServlet {
        public SlackOAuthRedirectController(App app) {
            super(app);
        }
    }
}