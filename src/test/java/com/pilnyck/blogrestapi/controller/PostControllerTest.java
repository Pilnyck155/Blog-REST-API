package com.pilnyck.blogrestapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.service.PostService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @MockBean
    private PostService postService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("test save post passed successfully")
    void whenDataIsValid_thenSavePost() throws Exception {
        Post post = Post.builder()
                .title("Sport")
                .content("Dynamo wins again")
                .build();

        when(postService.savePost(any())).thenReturn(post);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Sport\", \"content\": \"Dynamo wins again\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Sport"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Dynamo wins again"))
                .andDo(MockMvcResultHandlers.print());
        verify(postService).savePost(any(Post.class));
    }

    @Test
    @DisplayName("test get all posts passed successfully")
    void whenMethodIsCall_thenReturnAllPosts() throws Exception {
        Post post = Post.builder()
                .id(1L)
                .title("Animals")
                .content("The elephant escaped from zoo")
                .build();
        Post secondPost = Post.builder()
                .id(2L)
                .title("Fresh news")
                .content("Fresh stupid news")
                .build();

        when(postService.getAllPosts()).thenReturn(List.of(post, secondPost));
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/v1/posts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Animals"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Fresh news"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("test get all posts with title passed successfully")
    void whenMethodCallWithTitleParam_thenReturnAllPostsWithCurrentTitle() throws Exception {
        Post post = Post.builder()
                .id(1L)
                .title("Animals")
                .content("The elephant escaped from zoo")
                .build();
        Post secondPost = Post.builder()
                .id(2L)
                .title("Fresh news")
                .content("Fresh stupid news")
                .build();
        Post thirdPost = Post.builder()
                .id(3L)
                .title("Animals")
                .content("The rhino escaped from zoo")
                .build();

        when(postService.findAllPostsByTitle("Animals")).thenReturn(List.of(post, thirdPost));
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/v1/posts?title={title}", "Animals"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Animals"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Animals"))
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    @DisplayName("test get all posts sorted by title passed successfully")
    void whenMethodCallWithSortParam_thenReturnAllPostsSortedByCurrentTitle() throws Exception {
        Post post = Post.builder()
                .id(1L)
                .title("Politics")
                .content("The member of congress escaped from zoo")
                .build();
        Post secondPost = Post.builder()
                .id(2L)
                .title("Documents")
                .content("Secret documents disappeared")
                .build();
        Post thirdPost = Post.builder()
                .id(3L)
                .title("Animals")
                .content("The rhino escaped from zoo")
                .build();

        when(postService.findAllPostsSortedByTitle()).thenReturn(List.of(thirdPost, secondPost, post));
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/v1/posts?sort={title}", "Animals"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Animals"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Documents"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].title").value("Politics"))
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    @DisplayName("test get post by id passed successfully")
    void whenIdIsValid_thenReturnPostById() throws Exception {
        Post post = Post.builder()
                .id(1L)
                .title("Animals")
                .content("The elephant escaped from zoo")
                .build();
        when(postService.getById(1L)).thenReturn(post);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/posts/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Animals"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("The elephant escaped from zoo"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("test edit post by id passed successfully")
    void whenDataIsValid_thenSaveEditPost_andReturnEditedPost() throws Exception {
        long id = 1L;
        Post post = Post.builder()
                .title("Sport")
                .content("Dynamo wins again")
                .build();
        when(postService.editPostById(post, id)).thenReturn(post);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/posts/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Sport\", \"content\": \"Dynamo wins again\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Sport"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Dynamo wins again"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("test delete post by id passed successfully")
    void whenIdIsValid_thenPostDelete() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/posts/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Post delete sucсessful"))
                .andDo(MockMvcResultHandlers.print());
    }
}