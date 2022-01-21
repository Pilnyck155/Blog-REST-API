package com.pilnyck.blogrestapi.repository;

import com.pilnyck.blogrestapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    //Comment saveComment(Comment comment);
}
