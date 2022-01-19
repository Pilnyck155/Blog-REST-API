package com.pilnyck.blogrestapi.service;

import com.pilnyck.blogrestapi.entity.Post;

import java.util.List;

public interface PostService {
    Post savePost(Post post);

    List<Post> getAllPosts();

    Post getById(long id);

    void deletePostById(long id);

    Post editPostById(Post post, long id);

    List<Post> findAllPostsByTitle(String title);

    List<Post> findAllPostsSortedByTitle();
}
