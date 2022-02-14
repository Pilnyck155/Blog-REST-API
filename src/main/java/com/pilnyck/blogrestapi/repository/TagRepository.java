package com.pilnyck.blogrestapi.repository;

import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("select u from Post u where u.tags = ?1")
    List<Post> findByTag(String tagName);

    boolean existsByTagName(String tagName);

    Tag getByTagName(String tagName);

    @Query("select p from Post p where p.tags in :tags")
    List<Post> findAllPostsByTags(List<String> tags);
}
