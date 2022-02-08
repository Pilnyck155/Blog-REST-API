package com.pilnyck.blogrestapi.controller;

import com.pilnyck.blogrestapi.dto.TagWithoutPostsDto;
import com.pilnyck.blogrestapi.entity.Tag;
import com.pilnyck.blogrestapi.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/posts")
public class TagController {

    Logger logger = LoggerFactory.getLogger(getClass());

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }


    @PostMapping("/{postId}/tags")
    private void saveTagByPostId(@RequestBody Tag tag, @PathVariable long postId) {
        logger.info("Save tag by postId {}", postId);
        tagService.saveTagByPostId(tag, postId);
    }

    @GetMapping("/tags")
    private List<TagWithoutPostsDto> findAllTags() {
        logger.info("Obtain list of all tags");
        return tagService.findAllTags();
    }

//    @GetMapping
//    private List<PostWithoutCommentDto> findAllPostsByTag(@RequestParam(value = "tag", required = false) String tagName) {
//        logger.info("Obtain all posts by tag {}", tagName);
//        return tagService.findAllPostsByTag(tagName);
//    }

    @DeleteMapping("tags/{tagId}")
    private void deleteTagById(@PathVariable long tagId) {
        logger.info("Delete tag by tagId {}", tagId);
        tagService.deleteTagById(tagId);
    }
}
