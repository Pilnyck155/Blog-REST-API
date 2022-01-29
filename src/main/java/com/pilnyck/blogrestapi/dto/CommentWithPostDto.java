package com.pilnyck.blogrestapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentWithPostDto {

    private long commentId;
    private String text;
    private LocalDateTime creationDate;
    private PostWithoutCommentDto postWithoutCommentDto;
}
