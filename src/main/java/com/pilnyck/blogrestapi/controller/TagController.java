package com.pilnyck.blogrestapi.controller;

import com.pilnyck.blogrestapi.dto.PostWithCommentsDto;
import com.pilnyck.blogrestapi.dto.PostWithoutCommentDto;
import com.pilnyck.blogrestapi.dto.TagWithoutPostsDto;
import com.pilnyck.blogrestapi.entity.Tag;
import com.pilnyck.blogrestapi.service.interfaces.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/posts")
public class TagController {

    Logger logger = LoggerFactory.getLogger(getClass());

    private final TagService tagService;

    @Autowired
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

    //TODO: Make work correct this method
    @GetMapping("/tags/one")
    private List<PostWithCommentsDto> findAllPostsByTag(@RequestParam(value = "tag", required = false) String tagName) {
        logger.info("Obtain all posts by tag {}", tagName);
        List<PostWithCommentsDto> allPostsByTag = tagService.findAllPostsByTag(tagName);
        return allPostsByTag;
    }

    //TODO: Make work correct this method
    @GetMapping("/tags/all")
    private List<PostWithoutCommentDto> findAllPostsByCoupleTags(@RequestParam(value = "tags", required = false) List<String> parameters) {
        logger.info("Obtain all posts by tag {}", parameters);
        return tagService.findAllPostsByTagsList(parameters);
    }

    @DeleteMapping("tags/{tagId}")
    private void deleteTagById(@PathVariable long tagId) {
        logger.info("Delete tag by tagId {}", tagId);
        tagService.deleteTagById(tagId);
    }
}
