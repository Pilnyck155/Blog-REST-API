package com.pilnyck.blogrestapi.repository;

import com.pilnyck.blogrestapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByTitle(String title);

    List<Post> findAllByStarTrue();

    @Query(
            value = "UPDATE Post SET star=true WHERE id=?1 RETURNING *",
            nativeQuery = true
            )
    Post updatePostByIdAndSetTrue(long id);

    @Query(
            value = "UPDATE Post SET star=false WHERE id=?1 RETURNING *",
            nativeQuery = true
    )
    Post updatePostByIdAndSetFalse(long id);
}
