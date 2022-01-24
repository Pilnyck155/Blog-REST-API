package com.pilnyck.blogrestapi.controller;

import com.pilnyck.blogrestapi.entity.Comment;
import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.service.CommentService;
import com.pilnyck.blogrestapi.service.PostService;
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
    void addCommentToPostById() throws Exception {
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
    void getCommentsById() throws Exception {
        Post post = Post.builder()
                .postId(2L)
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
        Comment secondComment = Comment.builder()
                .commentId(2L)
                .creationDate(LocalDateTime.of(2021, Month.DECEMBER, 15, 11, 36, 42, 11))
                .text("This is Java for Android!")
                .post(post)
                .build();
        when(commentService.getCommentsByPostId(2L)).thenReturn(List.of(comment, secondComment));
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/posts/{id}/comments", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                .content("{\"text\": \"This is Java!\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getCommentsByPostIdAndCommentId() throws Exception {
        Post post = Post.builder()
                .postId(2L)
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
        Comment secondComment = Comment.builder()
                .commentId(2L)
                .creationDate(LocalDateTime.of(2021, Month.DECEMBER, 15, 11, 36, 42, 11))
                .text("This is Java for Android!")
                .post(post)
                .build();
        when(commentService.getCommentsByPostIdAndCommentId(2L, 1L)).thenReturn(List.of(comment));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/posts/{postId}/comment/{commentId}", 2, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\": \"This is Java!\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
                .andDo(MockMvcResultHandlers.print());
    }
}