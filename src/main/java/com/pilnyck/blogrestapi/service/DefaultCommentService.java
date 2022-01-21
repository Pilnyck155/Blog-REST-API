package com.pilnyck.blogrestapi.service;

import com.pilnyck.blogrestapi.entity.Comment;
import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.repository.CommentRepository;
import com.pilnyck.blogrestapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return commentRepository.save(comment);
        //return commentRepository.saveComment(comment);
    }
}
