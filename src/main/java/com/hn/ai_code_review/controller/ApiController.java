package com.hn.ai_code_review.controller;

import com.hn.ai_code_review.dto.GithubWebhookRequest;
import com.hn.ai_code_review.service.GithubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {

    private final GithubService githubService;

    @PostMapping("/github/webhook")
    public ResponseEntity<Void> webhook(
            @RequestBody GithubWebhookRequest payload) {

        log.info("payload : {}",payload);
        String result = githubService.getPullRequestFiles(payload);
        log.info("result : {}", result);

        return ResponseEntity.ok().build();
    }

}
