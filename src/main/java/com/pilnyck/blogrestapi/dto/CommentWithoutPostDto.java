package com.pilnyck.blogrestapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentWithoutPostDto {
    private long commentId;
    private String text;
    private LocalDateTime creationDate;
}
