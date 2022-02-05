package com.pilnyck.blogrestapi.controller;

import com.pilnyck.blogrestapi.dto.TagWithoutPostsDto;
import com.pilnyck.blogrestapi.entity.Comment;
import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.entity.Tag;
import com.pilnyck.blogrestapi.service.PostService;
import com.pilnyck.blogrestapi.service.TagService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(TagController.class)
class TagControllerTest {

    @MockBean
    private TagService tagService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("test call saveTagByPostId method passed successfully")
    public void whenCallTheMethod_thenCheckIfMethodWasCalled() throws Exception {
        Post post = Post.builder()
                .postId(3L)
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
        Tag tag = Tag.builder()
                .tagId(7L)
                .tagName("Programing language")
                .build();
        post.setComments(List.of(comment));
        post.setTags(Set.of(tag));

        doNothing().when(tagService).saveTagByPostId(isA(Tag.class), isA(Long.class));
        tagService.saveTagByPostId(tag, 3);
        verify(tagService, times(1)).saveTagByPostId(tag, 3);
    }


    @Test
    @DisplayName("test find all tags method passed successfully")
    public void whenCallMethod_thenReturnAllTags() throws Exception {
        TagWithoutPostsDto firstTag = new TagWithoutPostsDto(10L, "#Java");
        TagWithoutPostsDto secondTag = new TagWithoutPostsDto(11L, "#SpringBoot");

        when(tagService.findAllTags()).thenReturn(List.of(firstTag, secondTag));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/posts/tags"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tagId").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].tagId").value(11))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tagName").value("#Java"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].tagName").value("#SpringBoot"));
    }

    @Test
    @DisplayName("test delete tag by id method passed successfully")
    public void whenCallDeleteMethod_thenCheckIfDeleteMethodWasCalled() throws Exception {
        doNothing().when(tagService).deleteTagById(isA(Long.class));
        tagService.deleteTagById(10L);
        verify(tagService, times(1)).deleteTagById(10);
    }
}