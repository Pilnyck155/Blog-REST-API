package com.pilnyck.blogrestapi.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Builder
@Data
public class PostWithCommentsDto {
    private long postId;
    private String title;
    private String content;
    private boolean star;
    private List<CommentWithoutPostDto> comments;
    private Set<TagWithoutPostsDto> tags;
    //private List<TagWithoutPostsDto> tags;
}
