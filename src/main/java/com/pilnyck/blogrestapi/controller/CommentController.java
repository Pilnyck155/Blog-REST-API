package com.pilnyck.blogrestapi.controller;

import com.pilnyck.blogrestapi.entity.Comment;
import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
public class CommentController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommentService commentService;

    //    POST /api/v1/posts/1/comments
    @PostMapping("/{id}/comments")
    public Comment addCommentToPostById(@PathVariable long id,
                                     @RequestBody Comment comment) {
        logger.info("in addCommentToPostById method");
        //comment.setPostId(id);
        return commentService.saveComment(id, comment);
    }

    //    GET /api/v1/posts/{id}/comments
    @GetMapping("/{id}/comments")
    public void getCommentsById(@RequestParam(value = "id", required = false) long id) {
        logger.info("in getCommentsById method");

    }

    //    GET /api/v1/posts/{postId}/comment/{commentId}
    @GetMapping("/{postId}/comment/{commentId}")
    public Post getCommentsByPostIdAndCommentId(@RequestParam(value = "postId", required = false) long postId,
                                                @RequestParam(value = "commentId", required = false) long commentId) {
        logger.info("in getCommentsByPostIdAndCommentId method");
        return null;
    }
}
