package com.hn.ai_code_review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PullRequestFile {

    private String filename;
    private String status;
    private String patch;

}
