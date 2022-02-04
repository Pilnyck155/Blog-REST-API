package com.pilnyck.blogrestapi.service;

import com.pilnyck.blogrestapi.dto.PostWithoutCommentDto;
import com.pilnyck.blogrestapi.dto.TagWithoutPostsDto;
import com.pilnyck.blogrestapi.entity.Tag;

import java.util.List;

public interface TagService {
    void saveTagByPostId(Tag tag, long postId);

    //List<PostWithoutCommentDto> findAllPostsByTag(String tagName);

    List<TagWithoutPostsDto> findAllTags();

    void deleteTagById(long tagId);
}
