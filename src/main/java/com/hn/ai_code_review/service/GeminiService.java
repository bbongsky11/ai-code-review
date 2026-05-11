package com.hn.ai_code_review.service;

import com.hn.ai_code_review.dto.GeminiRequest;
import com.hn.ai_code_review.dto.GeminiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeminiService {

    @Qualifier("geminiWebClient")
    private final WebClient geminiWebClient;
    @Value("${core.gemini.api-key}")
    private String geminiApiKey;

    public GeminiResponse reviewCode(String diff) {

        String prompt = """
                당신은 시니어 Java/Spring 리뷰어입니다.

                아래 PR diff를 리뷰해주세요.

                리뷰 기준:
                - 가독성
                - 유지보수성
                - 성능
                - 리팩토링 포인트

                diff:
                """ + diff;

        var requestBody = new GeminiRequest(
                List.of(new GeminiRequest.Content(
                        List.of(new GeminiRequest.Part(prompt))
                ))
        );

        GeminiResponse response = geminiWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1beta/models/gemini-2.5-flash:generateContent")
                        .queryParam("key", geminiApiKey)
                        .build())
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(GeminiResponse.class)
                .block();

        log.info("API RESPONSE:{}", response);
        return response;

    }

}
