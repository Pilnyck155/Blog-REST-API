package com.pilnyck.blogrestapi.service;

import com.pilnyck.blogrestapi.entity.Comment;
import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.repository.CommentRepository;
import com.pilnyck.blogrestapi.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultCommentService implements CommentService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public Comment saveComment(long id, Comment comment) {
        Post postFromDB = postRepository.getById(id);
        //TODO: Add Optional when return Post from DB
        comment.setPost(postFromDB);
        Comment savedComment = commentRepository.save(comment);
        //logger.info("comment from DB {}", savedComment);
        return savedComment;
        //return commentRepository.saveComment(comment);
    }
}
