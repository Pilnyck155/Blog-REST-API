package com.pilnyck.blogrestapi.service.realization;

import com.pilnyck.blogrestapi.dto.TagWithoutPostsDto;
import com.pilnyck.blogrestapi.entity.Comment;
import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.entity.Tag;
import com.pilnyck.blogrestapi.repository.PostRepository;
import com.pilnyck.blogrestapi.repository.TagRepository;
import com.pilnyck.blogrestapi.service.interfaces.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DefaultTagServiceTest {
    private TagService tagService;
    private TagRepository tagRepository;
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        tagRepository = mock(TagRepository.class);
        postRepository = mock(PostRepository.class);
        tagService = new DefaultTagService(tagRepository, postRepository);

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
        Tag firstTag = Tag.builder()
                .tagId(7L)
                .tagName("Java")
                .posts(List.of(post))
                .build();
        Tag secondTag = Tag.builder()
                .tagId(8L)
                .tagName("Python")
                .build();
        Tag thirdTag = Tag.builder()
                .tagId(9L)
                .tagName("JavaScript")
                .build();
        post.setTags(Set.of(secondTag, thirdTag));
        when(tagRepository.findAll()).thenReturn(List.of(firstTag, secondTag, thirdTag));
        when(postRepository.getById(3L)).thenReturn(post);
        when(tagRepository.getByTagName("Java")).thenReturn(firstTag);
        when(tagRepository.save(firstTag)).thenReturn(firstTag);
        when(postRepository.save(post)).thenReturn(post);
    }

    @Test
    void saveTagByPostId() {
        Post firstPost = Post.builder()
                .postId(3L)
                .content("For start lear Java programing language you need learn Indian language")
                .title("Start in Java")
                .star(true)
                .build();
        Tag firstTag = Tag.builder()
                .tagId(7L)
                .tagName("Java")
                .build();
        firstPost.setTags(Set.of(firstTag));
        tagService.saveTagByPostId(firstTag, 3L);
        verify(postRepository, times(1)).getById(3L);
        verify(tagRepository, times(1)).getByTagName("Java");
        verify(tagRepository, times(0)).save(firstTag);
        verify(postRepository, times(1)).save(firstPost);
    }

    @Test
    void findAllTags() {
        List<TagWithoutPostsDto> allTags = tagService.findAllTags();
        int expectedSize = 3;
        assertEquals(expectedSize, allTags.size());
        assertEquals("Java", allTags.get(0).getTagName());
        assertEquals("Python", allTags.get(1).getTagName());
        assertEquals("JavaScript", allTags.get(2).getTagName());
        assertEquals(7, allTags.get(0).getTagId());
        assertEquals(8, allTags.get(1).getTagId());
        assertEquals(9, allTags.get(2).getTagId());
    }

    @Test
    void deleteTagById() {
        doNothing().when(tagRepository).deleteById(7L);
        tagRepository.deleteById(7L);
        verify(tagRepository, times(1)).deleteById(7L);
    }
}