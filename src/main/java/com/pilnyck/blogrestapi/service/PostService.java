package com.pilnyck.blogrestapi.service;

import com.pilnyck.blogrestapi.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post savePost(Post post);

    List<Post> getAllPosts();

    Post getById(long id);

    void deletePostById(long id);

    Post editPostById(Post post, long postId);

    List<Post> findAllPostsByTitle(String title);

    List<Post> findAllPostsSortedByTitle();

    List<Post> getAllPostsWithStar();

    Post addStarToPost(long id);

    Post deleteStarFromPost(long id);

    Optional<Post> getPostWithAllComments(long postId);
}
