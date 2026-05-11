package com.hn.ai_code_review.controller;

import com.hn.ai_code_review.dto.GithubWebhookRequest;
import com.hn.ai_code_review.dto.PullRequestFile;
import com.hn.ai_code_review.service.GeminiService;
import com.hn.ai_code_review.service.GithubService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {

    private final GithubService githubService;
    private final GeminiService geminiService;

    @PostMapping("/github/webhook")
    public ResponseEntity<Void> webhook(
            @RequestBody GithubWebhookRequest payload) {

        log.info("payload : {}",payload);
        List<PullRequestFile> result = githubService.getPullRequestFiles(payload);

        for (PullRequestFile file : result) {
            log.info("Patch : {}",file.getPatch());

            geminiService.reviewCode(file.getPatch());
        }

        return ResponseEntity.ok().build();
    }

}
