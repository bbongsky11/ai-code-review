package com.hn.ai_code_review.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${core.github.token}")
    private String githubToken;

    @Bean
    public WebClient githubWebClient() {

        return WebClient.builder()
                .baseUrl("https://api.github.com")
                .defaultHeader(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + githubToken
                )
                .build();
    }

}
