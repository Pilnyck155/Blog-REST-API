package com.pilnyck.blogrestapi.service;

import com.pilnyck.blogrestapi.entity.Comment;
import com.pilnyck.blogrestapi.entity.Post;

import java.util.List;


public interface CommentService {
    Comment saveComment(long id, Comment comment);

    List<Comment> getCommentsByPostId(long postId);

    List<Comment> getCommentsByPostIdAndCommentId(long postId, long commentId);
}
