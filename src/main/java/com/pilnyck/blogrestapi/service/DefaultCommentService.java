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
        return savedComment;
    }

    @Override
    public List<Comment> getCommentsByPostId(long postId) {
        Post postFromDB = postRepository.getById(postId);
        List<Comment> commentsFromPost = postFromDB.getComments();
        return commentsFromPost;
    }

    @Override
    public Comment getCommentByPostIdAndCommentId(long postId, long commentId) {
        Comment byPostIdAndCommentId = commentRepository.findCommentByPostIdAndCommentId(postId, commentId);
        return byPostIdAndCommentId;
    }
}
