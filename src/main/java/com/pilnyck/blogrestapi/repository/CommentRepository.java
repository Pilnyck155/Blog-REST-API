package com.pilnyck.blogrestapi.repository;

import com.pilnyck.blogrestapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c.commentId, c.creationDate, c.text, c.post.postId from Comment c where c.post.postId=?1 and c.commentId=?2")
    Comment findCommentByPostIdAndCommentId(long postId, long commentId);
}
