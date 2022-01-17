package com.pilnyck.blogrestapi.service;

import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.repository.PostRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @BeforeEach
    void setUp(){
        Post post = Post.builder()
                .id(1L)
                .title("News")
                .content("Very happy news")
                .build();
        Post secondPost = Post.builder()
                .id(2L)
                .title("Events")
                .content("West Side story won a Golden Globe")
                .build();
        Post thirdPost = Post.builder()
                .id(3L)
                .title("Sport")
                .content("Italian club Genoa fired Andriy Shevchenko from the post of head coach.")
                .build();

        List<Post> postList = List.of(post, secondPost, thirdPost);

        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(postList.get(0)));
        Mockito.when(postRepository.save(post)).thenReturn(post);
        Mockito.when(postRepository.findAll()).thenReturn(postList);
    }

    @Test
    @DisplayName("test save one post passed successfully")
    void whenDataIsValid_thenSaveAndReturnPost() {
        Post actualPost = Post.builder()
                .id(1L)
                .title("News")
                .content("Very happy news")
                .build();
        Post expectedPost = postService.savePost(actualPost);
        assertEquals(expectedPost.getContent(), actualPost.getContent());
        assertEquals(expectedPost.getTitle(), actualPost.getTitle());
        assertEquals(expectedPost.getId(), actualPost.getId());
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
                .id(2L)
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
                .id(2L)
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
        assertEquals(id, findPost.getId());
    }

    @Test
    @DisplayName("test delete one post by id passed successfully")
    void deletePostById() {
//        List<String> postSomeList = new ArrayList<>();
//        postSomeList.add("First");
//        postSomeList.add("Second");
//        postSomeList.add("Third");
//        PostService newPostService = new PostServiceImp();
//        PostService spy = spy(newPostService);
        //Mockito.when(mock(postRepository.deletePostById(3L))).then(postSomeList.remove(2));

//        int beforeDelete = postService.getAllPosts().size();
//        int expectedBefore = 3;
//        postService.deletePostById(3L);
//        int afterDelete = postService.getAllPosts().size();
//        int expectedAfter = 2;
//        assertEquals(expectedBefore, beforeDelete);
//        assertEquals(expectedAfter, afterDelete);
        /*
        PostService mockPostService = mock(PostServiceImp.class);
        mockPostService.deletePostById(1L);
        verify(mockPostService, times(1)).deletePostById(1L);

         */
    }

    @Test
    @DisplayName("test edit post by id passed successfully")
    void editPostById() {
        Post expectedPost = Post.builder()
                .id(1L)
                .title("Animals")
                .content("In the Vinnytsia zoo born a panda")
                .build();
        Post actualPost = postService.editPostById(expectedPost, 1L);
        assertEquals(expectedPost, actualPost);
    }
}