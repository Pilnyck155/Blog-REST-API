package com.pilnyck.blogrestapi.dto;

import lombok.Data;


@Data
public class PostWithoutCommentDto {
    private long postId;
    private String title;
    private String content;
    private boolean star;
}
