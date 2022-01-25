package com.pilnyck.blogrestapi.controller;

import com.pilnyck.blogrestapi.entity.Comment;
import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.service.CommentService;
import com.pilnyck.blogrestapi.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @MockBean
    private CommentService commentService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("test add comment to post by id passed successfully")
    void whenIdIsValid_thenSaveCommentToPostById() throws Exception {
        Post post = Post.builder()
                .postId(1L)
                .content("For start lear Java programing language you need learn Indian language")
                .title("Start in Java")
                .star(true)
                .build();
        Comment comment = Comment.builder()
                .commentId(1L)
                .creationDate(LocalDateTime.of(2022, Month.JANUARY, 10, 17, 43, 37, 5))
                .text("This is Java!")
                .post(post)
                .build();
        when(commentService.saveComment(anyLong(), any())).thenReturn(comment);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/posts/{id}/comments", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\": \"This is Java!\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("This is Java!"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("test get all comment by post id passed successfully")
    void whenIdIsValid_thenReturnAllCommentsByPostId() throws Exception {
        Comment comment = Comment.builder()
                .commentId(1L)
                .creationDate(LocalDateTime.of(2022, Month.JANUARY, 10, 17, 43, 37, 5))
                .text("This is Java!")
                .build();
        Comment secondComment = Comment.builder()
                .commentId(2L)
                .creationDate(LocalDateTime.of(2021, Month.DECEMBER, 15, 11, 36, 42, 11))
                .text("This is Java for Android!")
                .build();
        when(commentService.getCommentsByPostId(2L)).thenReturn(List.of(comment, secondComment));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/posts/{id}/comments", 2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].commentId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].text").value("This is Java!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].commentId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].text").value("This is Java for Android!"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("test get comment by post id and comment id passed successfully")
    void whenIdIsValid_thenReturnCommentById() throws Exception {
        Comment comment = Comment.builder()
                .commentId(1L)
                .creationDate(LocalDateTime.of(2022, Month.JANUARY, 10, 17, 43, 37, 5))
                .text("This is Java!")
                .build();
        when(commentService.getCommentsByPostIdAndCommentId(2L, 1L)).thenReturn(comment);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/posts/{postId}/comment/{commentId}", 2, 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("This is Java!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.commentId").value(1))
                .andDo(MockMvcResultHandlers.print());
    }
}