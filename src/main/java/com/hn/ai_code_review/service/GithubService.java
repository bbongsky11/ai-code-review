package com.hn.ai_code_review.service;

import com.hn.ai_code_review.dto.GithubWebhookRequest;
import com.hn.ai_code_review.dto.PullRequestFile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubService {

    @Qualifier("githubWebClient")
    private final WebClient githubWebClient;

    public List<PullRequestFile> getPullRequestFiles(GithubWebhookRequest payload) {

        String owner = payload.getRepository()
                .getOwner()
                .getLogin();
        String repo = payload.getRepository()
                .getName();
        Integer prNumber = payload.getPull_request()
                .getNumber();

        return githubWebClient.get()
                .uri("/repos/" + owner +
                        "/" + repo +
                        "/pulls/" + prNumber +
                        "/files")
                .retrieve()
                .bodyToFlux(PullRequestFile.class)
                .collectList()
                .block();
    }
}
