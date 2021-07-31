package com.sooman.slack_spring_integration;

import com.slack.api.bolt.App;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.bolt.service.OAuthStateService;
import com.slack.api.bolt.service.builtin.AmazonS3InstallationService;
import com.slack.api.bolt.service.builtin.AmazonS3OAuthStateService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlackApp {
    private static final String S3_BUCKET_NAME = "colawork-static-storage";

    @Bean
    public InstallationService initInstallationService() {
        InstallationService installationService = new AmazonS3InstallationService(S3_BUCKET_NAME);
        installationService.setHistoricalDataEnabled(true);
        return installationService;
    }

    @Bean
    public OAuthStateService initStateService() {
        return new AmazonS3OAuthStateService(S3_BUCKET_NAME);
    }

    @Bean
    public App initSlackApp(InstallationService installationService, OAuthStateService stateService) {
        App app = new App().asOAuthApp(true); // Do not forget calling `asOAuthApp(true)` here
        app.service(stateService);
        app.service(installationService);
        app.enableTokenRevocationHandlers();

        app.command("/hello-oauth-app", (req, ctx) -> ctx.ack("What's up?"));
        return app;
    }
}