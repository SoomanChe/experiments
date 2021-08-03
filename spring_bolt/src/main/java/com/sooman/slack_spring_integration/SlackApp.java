package com.sooman.slack_spring_integration;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.handler.WebEndpointHandler;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.bolt.service.OAuthStateService;
import com.slack.api.bolt.service.builtin.AmazonS3InstallationService;
import com.slack.api.bolt.service.builtin.AmazonS3OAuthStateService;
import com.slack.api.bolt.servlet.SlackAppServletAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Configuration
public class SlackApp {
    private static final String S3_BUCKET_NAME = "colawork-slack-oauth";

    @Bean
    public InstallationService initInstallationService() {
        return new AmazonS3InstallationService(S3_BUCKET_NAME);
    }

    @Bean
    public OAuthStateService initStateService() {
        return new AmazonS3OAuthStateService(S3_BUCKET_NAME);
    }

    @Bean
    public App initSlackApp(InstallationService installationService, OAuthStateService stateService) {
        AppConfig appConfig = AppConfig.builder().oAuthInstallPageRenderingEnabled(false).build();
        App app = new App(appConfig).asOAuthApp(true);
        app.service(stateService);
        app.service(installationService);
        app.enableTokenRevocationHandlers();

        return app;
    }
}