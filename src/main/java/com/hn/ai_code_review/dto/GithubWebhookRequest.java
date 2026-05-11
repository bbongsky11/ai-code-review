package com.hn.ai_code_review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GithubWebhookRequest {

    private String action;
    private Repository repository;
    private PullRequest pull_request;

    @Getter
    @Setter
    public static class Repository {
        private String name;
        private Owner owner;
    }

    @Getter
    @Setter
    public static class Owner {
        private String login;
    }

    @Getter
    @Setter
    public static class PullRequest {
        private Integer number;
    }

}
