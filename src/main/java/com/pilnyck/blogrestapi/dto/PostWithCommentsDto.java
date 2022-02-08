package com.pilnyck.blogrestapi.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;


@Data
public class PostWithCommentsDto {
    private long postId;
    private String title;
    private String content;
    private boolean star;
    private List<CommentWithoutPostDto> comments;
    private Set<TagWithoutPostsDto> tags;
}
