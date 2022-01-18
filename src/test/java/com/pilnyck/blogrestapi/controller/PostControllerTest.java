package com.pilnyck.blogrestapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.service.PostService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @MockBean
    private PostService postService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldSavePost() throws Exception {
//        Post post = Post.builder()
//                .title("Sport")
//                .content("Dynamo wins")
//                .build();

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                       // .content(objectToJson(post)))
                        .content("{\"title\": \"Sport\", \"content\": \"Dynamo wins again\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Sport"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print());
        verify(postService).savePost(any(Post.class));
    }

    @Test
    void shouldGetAllPosts() throws Exception {
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
    void getById() throws Exception {
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
    void editPostById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/posts/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        //.contentType("application/json;charset=UTF-8")
                        //.characterEncoding("UTF-8")
                        .content("{\"id\": \"1\", \"title\": \"Sport\", \"content\": \"Dynamo wins again\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Sport"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Dynamo wins again"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void deletePostById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/posts/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Post delete sucсessful"))
                .andDo(MockMvcResultHandlers.print());
    }

//    public static String objectToJson(Object object) throws JsonProcessingException {
//        return new ObjectMapper().writeValueAsString(object);
//    }
}