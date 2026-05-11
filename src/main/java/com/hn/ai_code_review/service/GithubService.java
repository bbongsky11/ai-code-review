package com.hn.ai_code_review.service;

import com.hn.ai_code_review.dto.GithubWebhookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class GithubService {
    private final WebClient webClient;

    public String getPullRequestFiles(GithubWebhookRequest payload) {

        String owner = payload.getRepository()
                .getOwner()
                .getLogin();
        String repo = payload.getRepository()
                .getName();
        Integer prNumber = payload.getPull_request()
                .getNumber();

        return webClient.get()
                .uri("/repos/" + owner +
                        "/" + repo +
                        "/pulls/" + prNumber +
                        "/files")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
