package com.pilnyck.blogrestapi.service;

import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.entity.Tag;
import com.pilnyck.blogrestapi.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        Tag firstTag = Tag.builder()
                .tagId(7L)
                .tagName("Panda")
                .build();

        Post post = Post.builder()
                .postId(1L)
                .title("News")
                .content("Very happy news")
                .build();
        Post secondPost = Post.builder()
                .postId(2L)
                .title("Events")
                .content("West Side story won a Golden Globe")
                .build();
        Post thirdPost = Post.builder()
                .postId(3L)
                .title("Sport")
                .content("Italian club Genoa fired Andriy Shevchenko from the post of head coach.")
                .build();
        Post fourthPost = Post.builder()
                .postId(1L)
                .title("Animals")
                .content("In the Vinnytsia zoo born a panda")
                .tags(Set.of(firstTag))
                .build();

        List<Post> postList = List.of(post, secondPost, thirdPost);

        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(postList.get(0)));
        Mockito.when(postRepository.save(post)).thenReturn(post);
        Mockito.when(postRepository.findAll()).thenReturn(postList);
        Mockito.when(postRepository.save(any(Post.class))).thenReturn(fourthPost);
    }

    @Test
    @DisplayName("test save one post passed successfully")
    void whenDataIsValid_thenSaveAndReturnPost() {
        Tag firstTag = Tag.builder()
                .tagId(7L)
                .tagName("Panda")
                .build();
        Post actualPost = Post.builder()
                .postId(1L)
                .title("Animals")
                .content("In the Vinnytsia zoo born a panda")
                .tags(Set.of(firstTag))
                .build();
        Post expectedPost = postService.savePost(actualPost);
        assertEquals(expectedPost.getContent(), actualPost.getContent());
        assertEquals(expectedPost.getTitle(), actualPost.getTitle());
        assertEquals(expectedPost.getPostId(), actualPost.getPostId());
    }

    @Test
    @DisplayName("test get all posts by count posts passed successfully")
    void whenCallMethod_thenReturnAllPostsSize() {
        List<Post> actualPostList = postService.getAllPosts();
        int expectedSize = 3;
        assertEquals(expectedSize, actualPostList.size());
    }

    @Test
    @DisplayName("test get all posts and check ont post passed successfully")
    void whenCallMethod_thenReturnAllPostsAndCheckPost() {
        List<Post> actualPostList = postService.getAllPosts();
        Post expected = Post.builder()
                .postId(2L)
                .title("Events")
                .content("West Side story won a Golden Globe")
                .build();
        assertEquals(expected, actualPostList.get(1));
    }

    @Test
    @DisplayName("test get all posts and check title passed successfully")
    void whenCallMethod_thenReturnAllPostsAndCheckTitles() {
        List<Post> actualPostList = postService.getAllPosts();
        Post expected = Post.builder()
                .postId(2L)
                .title("Events")
                .content("West Side story won a Golden Globe")
                .build();
        assertEquals(expected.getTitle(), actualPostList.get(1).getTitle());
    }

    @Test
    @DisplayName("test get one post passed successfully")
    void whenIdIsValid_thenPostShouldFound() {
        long id = 1L;
        Post findPost = postService.getById(id);
        assertEquals(id, findPost.getPostId());
    }

    @Test
    @DisplayName("test delete one post by id passed successfully")
    void deletePostById() {

    }

    @Test
    @DisplayName("test edit post by id passed successfully")
    void editPostById() {
        Tag firstTag = Tag.builder()
                .tagId(7L)
                .tagName("Panda")
                .build();
        Post expectedPost = Post.builder()
                .postId(1L)
                .title("Animals")
                .content("In the Vinnytsia zoo born a panda")
                .tags(Set.of(firstTag))
                .build();
        Post actualPost = postService.editPostById(expectedPost, 1L);
        assertEquals(expectedPost, actualPost);
    }
}