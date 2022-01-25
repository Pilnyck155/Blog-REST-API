package com.pilnyck.blogrestapi.dto;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.time.LocalDateTime;
@Data
public class CommentWithPostDto {

    private long commentId;
    private String text;
    private LocalDateTime creationDate;
    private PostWithoutCommentDto postWithoutCommentDto;
}
