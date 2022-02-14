package com.pilnyck.blogrestapi.service.interfaces;

import com.pilnyck.blogrestapi.entity.Comment;

import java.util.List;


public interface CommentService {
    Comment saveComment(long id, Comment comment);

    List<Comment> getCommentsByPostId(long postId);

    Comment getCommentByPostIdAndCommentId(long postId, long commentId);
}
