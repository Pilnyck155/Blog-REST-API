package com.pilnyck.blogrestapi.service;

import com.pilnyck.blogrestapi.entity.Comment;
import com.pilnyck.blogrestapi.entity.Post;


public interface CommentService {
    Comment saveComment(long id, Comment comment);
}
