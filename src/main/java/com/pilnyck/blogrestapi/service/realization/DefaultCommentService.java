package com.pilnyck.blogrestapi.service.realization;

import com.pilnyck.blogrestapi.entity.Comment;
import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.repository.CommentRepository;
import com.pilnyck.blogrestapi.repository.PostRepository;
import com.pilnyck.blogrestapi.service.interfaces.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultCommentService implements CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    @Autowired
    public DefaultCommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Comment saveComment(long id, Comment comment) {
        Post postFromDB = postRepository.getById(id);
        comment.setPost(postFromDB);
        Comment savedComment = commentRepository.save(comment);
        return savedComment;
    }

    @Override
    public List<Comment> getCommentsByPostId(long postId) {
        Post postFromDB = postRepository.getById(postId);
        return postFromDB.getComments();
    }

    @Override
    public Comment getCommentByPostIdAndCommentId(long postId, long commentId) {
        return commentRepository.findCommentByPostIdAndCommentId(postId, commentId);
    }
}
