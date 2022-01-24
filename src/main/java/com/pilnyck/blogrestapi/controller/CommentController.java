package com.pilnyck.blogrestapi.controller;

import com.pilnyck.blogrestapi.dto.CommentWithPostDto;
import com.pilnyck.blogrestapi.dto.PostWithoutCommentDto;
import com.pilnyck.blogrestapi.entity.Comment;
import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class CommentController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommentService commentService;

    //    POST /api/v1/posts/1/comments
    @PostMapping("/{id}/comments")
    public CommentWithPostDto addCommentToPostById(@PathVariable long id,
                                                   @RequestBody Comment comment) {
        logger.info("in addCommentToPostById method");
        Comment commentFromDB = commentService.saveComment(id, comment);
        CommentWithPostDto commentWithPostDto = toCommentWithPostDto(commentFromDB);
        return commentWithPostDto;
    }


    //    GET /api/v1/posts/{id}/comments
    @GetMapping("/{id}/comments")
    public List<Comment> getCommentsById(@RequestParam(value = "id", required = false) long postId) {
        logger.info("in getCommentsById method");
        return commentService.getCommentsByPostId(postId);

    }

    //    GET /api/v1/posts/{postId}/comment/{commentId}
    @GetMapping("/{postId}/comment/{commentId}")
    public List<Comment> getCommentsByPostIdAndCommentId(@RequestParam(value = "postId", required = false) long postId,
                                                @RequestParam(value = "commentId", required = false) long commentId) {
        logger.info("in getCommentsByPostIdAndCommentId method");
        return commentService.getCommentsByPostIdAndCommentId(postId, commentId);
    }

    private CommentWithPostDto toCommentWithPostDto(Comment comment) {
        CommentWithPostDto commentWithPostDto = new CommentWithPostDto();
        commentWithPostDto.setCommentId(comment.getCommentId());
        commentWithPostDto.setText(comment.getText());
        commentWithPostDto.setCreationDate(comment.getCreationDate());

        Post post = comment.getPost();
        PostWithoutCommentDto postWithoutCommentDto = new PostWithoutCommentDto();
        postWithoutCommentDto.setPostId(post.getPostId());
        postWithoutCommentDto.setContent(post.getContent());
        postWithoutCommentDto.setStar(post.isStar());
        postWithoutCommentDto.setTitle(post.getTitle());

        commentWithPostDto.setPostWithoutCommentDto(postWithoutCommentDto);
        return commentWithPostDto;
    }
}
