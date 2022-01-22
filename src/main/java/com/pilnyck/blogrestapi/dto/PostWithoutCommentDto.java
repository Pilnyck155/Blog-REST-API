package com.pilnyck.blogrestapi.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class PostWithoutCommentDto {
    private long postId;

    private String title;

    private String content;
    private boolean star;
}
