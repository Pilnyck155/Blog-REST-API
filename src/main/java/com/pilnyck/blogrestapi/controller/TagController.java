package com.pilnyck.blogrestapi.controller;

import com.pilnyck.blogrestapi.dto.PostWithoutCommentDto;
import com.pilnyck.blogrestapi.dto.TagWithoutPostsDto;
import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.entity.Tag;
import com.pilnyck.blogrestapi.service.PostService;
import com.pilnyck.blogrestapi.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/posts")
public class TagController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TagService tagService;

    @Autowired
    private PostService postService;

    @PostMapping("/{postId}/tags")
    private void saveTagByPostId(@RequestBody Tag tag, @PathVariable long postId) {
        tagService.saveTagByPostId(tag, postId);
    }

    @GetMapping("/tags")
    private List<TagWithoutPostsDto> findAllTags() {
        List<TagWithoutPostsDto> listOfTags = tagService.findAllTags();
        return listOfTags;
    }

//    @GetMapping
//    private List<PostWithoutCommentDto> findAllPostsByTag(@RequestParam(value = "tag", required = false) String tagName) {
//        logger.info("Obtain all posts by tag {}", tagName);
//        return tagService.findAllPostsByTag(tagName);
//    }

    @DeleteMapping("tags/{tagId}")
    private void deleteTagById(@PathVariable long tagId) {
        tagService.deleteTagById(tagId);
    }
}
