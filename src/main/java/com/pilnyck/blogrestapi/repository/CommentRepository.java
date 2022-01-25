package com.pilnyck.blogrestapi.repository;

import com.pilnyck.blogrestapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(
            value = "SELECT comment_id, creation_date, text, post_id FROM comment WHERE post_id=?1 AND comment_id=?2",
            nativeQuery = true
    )
    Comment findCommentByPostIdAndCommentId(long postId, long commentId);
}
