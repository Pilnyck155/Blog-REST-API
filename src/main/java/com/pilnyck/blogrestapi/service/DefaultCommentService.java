package com.pilnyck.blogrestapi.service;

import com.pilnyck.blogrestapi.entity.Comment;
import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.repository.CommentRepository;
import com.pilnyck.blogrestapi.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    }

    @Override
    public List<Comment> getCommentsByPostId(long postId) {
        Post postFromDB = postRepository.getById(postId);
        List<Comment> commentsFromPost = postFromDB.getComments();
        logger.info("comments by post id {}", commentsFromPost);
        return commentsFromPost;
    }

    @Override
    public List<Comment> getCommentsByPostIdAndCommentId(long postId, long commentId) {
        return commentRepository.findByPostIdAndCommentId(postId, commentId);
    }
}
