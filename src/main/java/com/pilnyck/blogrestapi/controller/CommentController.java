package com.pilnyck.blogrestapi.controller;

import com.pilnyck.blogrestapi.dto.CommentWithPostDto;
import com.pilnyck.blogrestapi.dto.CommentWithoutPostDto;
import com.pilnyck.blogrestapi.dto.PostWithoutCommentDto;
import com.pilnyck.blogrestapi.entity.Comment;
import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.service.interfaces.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class CommentController {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{id}/comments")
    public CommentWithPostDto addCommentToPostById(@PathVariable long id,
                                                   @RequestBody Comment comment) {
        logger.info("Add new comment by id {}", id);
        Comment commentFromDB = commentService.saveComment(id, comment);
        CommentWithPostDto commentWithPostDto = toCommentWithPostDto(commentFromDB);
        return commentWithPostDto;
    }

    @GetMapping("/{postId}/comments")
    public List<CommentWithoutPostDto> getCommentsByPostId(@PathVariable long postId) {
        logger.info("Obtain all comments by post id {}", postId);
        List<Comment> commentsByPostId = commentService.getCommentsByPostId(postId);
        List<CommentWithoutPostDto> listComments = new ArrayList<>(commentsByPostId.size());
        for (Comment comment : commentsByPostId) {
            listComments.add(toCommentWithoutPostDto(comment));
        }
        return listComments;

    }

    @GetMapping("/{postId}/comment/{commentId}")
    //TODO: Rewrite method by using only commentId
    public CommentWithoutPostDto getCommentByPostIdAndCommentId(@PathVariable long postId,
                                                                @PathVariable long commentId) {
        logger.info("Obtain all comments by post id {} and comment id {}", postId, commentId);
        Comment commentFromDB = commentService.getCommentByPostIdAndCommentId(postId, commentId);

        CommentWithoutPostDto commentWithoutPostDto = toCommentWithoutPostDto(commentFromDB);
        return commentWithoutPostDto;
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

    private CommentWithoutPostDto toCommentWithoutPostDto(Comment comment) {
        CommentWithoutPostDto commentWithoutPostDto = new CommentWithoutPostDto();
        commentWithoutPostDto.setCommentId(comment.getCommentId());
        commentWithoutPostDto.setText(comment.getText());
        commentWithoutPostDto.setCreationDate(comment.getCreationDate());
        return commentWithoutPostDto;
    }
}
