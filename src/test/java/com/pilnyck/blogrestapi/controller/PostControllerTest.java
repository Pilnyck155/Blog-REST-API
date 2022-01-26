package com.pilnyck.blogrestapi.controller;

import com.pilnyck.blogrestapi.entity.Comment;
import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.service.PostService;
import net.bytebuddy.dynamic.DynamicType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                .postId(1L)
                .title("Animals")
                .content("The elephant escaped from zoo")
                .build();
        Post secondPost = Post.builder()
                .postId(2L)
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
                .postId(1L)
                .title("Animals")
                .content("The elephant escaped from zoo")
                .build();
        Post secondPost = Post.builder()
                .postId(2L)
                .title("Fresh news")
                .content("Fresh stupid news")
                .build();
        Post thirdPost = Post.builder()
                .postId(3L)
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
                .postId(1L)
                .title("Politics")
                .content("The member of congress escaped from zoo")
                .build();
        Post secondPost = Post.builder()
                .postId(2L)
                .title("Documents")
                .content("Secret documents disappeared")
                .build();
        Post thirdPost = Post.builder()
                .postId(3L)
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
                .postId(1L)
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


    @Test
    @DisplayName("test get all posts with star passed successfully")
    public void whenCallMethod_thenReturnAllPostsWithStar() throws Exception {
        Post post = Post.builder()
                .postId(1L)
                .title("Animals")
                .content("The elephant escaped from zoo")
                .star(true)
                .build();
        Post secondPost = Post.builder()
                .postId(2L)
                .title("Fresh news")
                .content("Fresh stupid news")
                .star(true)
                .build();

        when(postService.getAllPostsWithStar()).thenReturn(List.of(post, secondPost));
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/v1/posts/star"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].star").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].star").isBoolean())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    @DisplayName("test add star to post passed successfully")
    public void whenIdIsValid_thenAddStarToPostById() throws Exception {
        Post post = Post.builder()
                .postId(1L)
                .title("Animals")
                .content("The elephant escaped from zoo")
                .star(true)
                .build();
        when(postService.addStarToPost(1L)).thenReturn(post);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/posts/{id}/star", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.star").value(true));
    }

    //deleteStarFromPost
    @Test
    @DisplayName("test delete star from post passed successfully")
    public void whenIdIsValid_thenDeleteStarFromPostById() throws Exception {
        Post post = Post.builder()
                .postId(1L)
                .title("Animals")
                .content("The elephant escaped from zoo")
                .star(false)
                .build();
        when(postService.deleteStarFromPost(1L)).thenReturn(post);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/posts/{id}/star", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.star").value(false));
    }

    //get post with all comments
    @Test
    @DisplayName("test get post with all comments passed successfully")
    public void whenIdIsValid_thenReturnPostWithAllComments() throws Exception {
        Post post = Post.builder()
                .postId(1L)
                .content("For start lear Java programing language you need learn Indian language")
                .title("Start in Java")
                .star(true)
                .build();
        Comment firstComment = Comment.builder()
                .commentId(1L)
                .creationDate(LocalDateTime.of(2022, Month.JANUARY, 10, 17, 43, 37, 5))
                .text("This is Java!")
                .post(post)
                .build();
        Comment secondComment = Comment.builder()
                .commentId(2L)
                .creationDate(LocalDateTime.of(2021, Month.FEBRUARY, 7, 11, 27, 18, 4))
                .text("This is Python!")
                .post(post)
                .build();
        List<Comment> commentList = List.of(firstComment, secondComment);
        post.setComments(commentList);
        Optional<Post> optionalPost = Optional.of(post);

        when(postService.getPostWithAllComments(1L)).thenReturn(optionalPost);
        //when(postService.getPostWithAllComments(1L)).thenReturn(optionalPost);
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/posts/{id}/full", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.star").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Start in Java"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.comments.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.comments.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.comments[0].text").value("This is Java!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.comments[1].text").value("This is Python!"));
    }

}