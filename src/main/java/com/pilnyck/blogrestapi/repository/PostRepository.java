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

    @Query(value = "update Post p set p.star=true where p.postId=?1")
    Post updatePostByIdAndSetTrue(long id);

    @Query(value = "update Post p set p.star=false where p.postId=?1")
    Post updatePostByIdAndSetFalse(long id);
}
