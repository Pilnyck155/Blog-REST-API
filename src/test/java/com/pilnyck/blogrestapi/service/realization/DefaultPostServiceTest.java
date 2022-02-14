package com.pilnyck.blogrestapi.service.realization;

import com.pilnyck.blogrestapi.entity.Comment;
import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.entity.Tag;
import com.pilnyck.blogrestapi.repository.PostRepository;
import com.pilnyck.blogrestapi.service.interfaces.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class DefaultPostServiceTest {

    private PostService postService;

    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        postRepository = mock(PostRepository.class);
        postService = new DefaultPostService(postRepository);

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
                //.tags(List.of(firstTag))
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
        doNothing().when(postRepository).deleteById(7L);
        postRepository.deleteById(7L);
        verify(postRepository, times(1)).deleteById(7L);
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

    @Test
    @DisplayName("test find all posts by title passed successfully")
    void whenCallMethodWithValidTitle_thenReturnListOfPostsByTitle() {
        Post post = Post.builder()
                .postId(1L)
                .title("News")
                .content("Very happy news")
                .build();
        Post secondPost = Post.builder()
                .postId(2L)
                .title("News")
                .content("West Side story won a Golden Globe")
                .build();
        Post thirdPost = Post.builder()
                .postId(3L)
                .title("News")
                .content("Italian club Genoa fired Andriy Shevchenko from the post of head coach.")
                .build();
        List<Post> postList = List.of(post, secondPost, thirdPost);

        Mockito.when(postRepository.findAllByTitle("News")).thenReturn(postList);
        List<Post> actualPostList = postService.findAllPostsByTitle("News");
        assertEquals(3, actualPostList.size());
        assertEquals("News", actualPostList.get(0).getTitle());
        assertEquals("News", actualPostList.get(1).getTitle());
        assertEquals("News", actualPostList.get(2).getTitle());
    }

    @Test
    @DisplayName("test find all posts sorted by title passed successfully")
    void whenCallMethodWithValidTitle_thenReturnListOfSortedPostsByTitle() {
        Post post = Post.builder()
                .postId(1L)
                .title("Animals")
                .content("Very happy news")
                .build();
        Post secondPost = Post.builder()
                .postId(2L)
                .title("Globe")
                .content("West Side story won a Golden Globe")
                .build();
        Post thirdPost = Post.builder()
                .postId(3L)
                .title("Shevchenko")
                .content("Italian club Genoa fired Andriy Shevchenko from the post of head coach.")
                .build();
        List<Post> postList = List.of(post, secondPost, thirdPost);

        Mockito.when(postRepository.findAll(Sort.by(Sort.Direction.ASC, "title"))).thenReturn(postList);
        List<Post> actualPostList = postService.findAllPostsSortedByTitle();
        assertEquals(3, actualPostList.size());
        assertEquals("Animals", actualPostList.get(0).getTitle());
        assertEquals("Globe", actualPostList.get(1).getTitle());
        assertEquals("Shevchenko", actualPostList.get(2).getTitle());
    }

    @Test
    @DisplayName("test get all posts with star passed successfully")
    void whenCallMethod_thenReturnListOfPostsWithStar() {
        Post post = Post.builder()
                .postId(1L)
                .title("Animals")
                .content("Very happy news")
                .star(true)
                .build();
        Post secondPost = Post.builder()
                .postId(2L)
                .title("Globe")
                .content("West Side story won a Golden Globe")
                .star(true)
                .build();
        Post thirdPost = Post.builder()
                .postId(3L)
                .title("Shevchenko")
                .content("Italian club Genoa fired Andriy Shevchenko from the post of head coach.")
                .star(true)
                .build();
        List<Post> postList = List.of(post, secondPost, thirdPost);

        Mockito.when(postRepository.findAllByStarTrue()).thenReturn(postList);
        List<Post> actualPostList = postService.getAllPostsWithStar();
        assertEquals(3, actualPostList.size());
        assertTrue(actualPostList.get(0).isStar());
        assertTrue(actualPostList.get(1).isStar());
        assertTrue(actualPostList.get(2).isStar());
    }

    @Test
    @DisplayName("test add start to post passed successfully")
    void whenIdIsValid_thenSetStarToTrueAndReturnPostWithStar() {
        Post post = Post.builder()
                .postId(7L)
                .title("Animals")
                .content("Very happy news")
                .star(true)
                .build();

        Mockito.when(postRepository.updatePostByIdAndSetTrue(7L)).thenReturn(post);
        Post postFromDB = postService.addStarToPost(7L);
        assertEquals(true, postFromDB.isStar());
        assertEquals(7, postFromDB.getPostId());
    }

    @Test
    @DisplayName("test delete start from post passed successfully")
    void whenIdIsValid_thenSetStarToFalseAndReturnPostWithStar() {
        Post post = Post.builder()
                .postId(7L)
                .title("Animals")
                .content("Very happy news")
                .star(false)
                .build();

        Mockito.when(postRepository.updatePostByIdAndSetFalse(7L)).thenReturn(post);
        Post postFromDB = postService.deleteStarFromPost(7L);
        assertEquals(false, postFromDB.isStar());
        assertEquals(7, postFromDB.getPostId());
    }

    @Test
    @DisplayName("test get post with all comments passed successfully")
    void whenIdIsValid_thenObtainPostWithAllComments() {
        Post post = Post.builder()
                .postId(7L)
                .title("Programing")
                .content("Very happy news")
                .star(false)
                .build();
        Comment firstComment = Comment.builder()
                .commentId(3L)
                .creationDate(LocalDateTime.of(2022, Month.JANUARY, 10, 17, 43, 37, 5))
                .text("This is Java!")
                .post(post)
                .build();
        Comment secondComment = Comment.builder()
                .commentId(4L)
                .creationDate(LocalDateTime.of(2021, Month.FEBRUARY, 18, 14, 23, 30, 3))
                .text("This is Python!")
                .post(post)
                .build();
        Comment thirdComment = Comment.builder()
                .commentId(5L)
                .creationDate(LocalDateTime.of(2020, Month.MARCH, 28, 9, 12, 11, 1))
                .text("This is JavaScript!")
                .post(post)
                .build();
        post.setComments(List.of(firstComment, secondComment, thirdComment));
        Optional<Post> optionalPost = Optional.of(post);

        Mockito.when(postRepository.findById(7L)).thenReturn(optionalPost);
        Optional<Post> actualPost = postService.getPostWithAllComments(7L);
        assertEquals(true, actualPost.isPresent());
        assertEquals(7L, actualPost.get().getPostId());
        assertEquals("Programing", actualPost.get().getTitle());
        assertEquals(3, actualPost.get().getComments().size());
        assertEquals("This is Java!", actualPost.get().getComments().get(0).getText());
        assertEquals("This is Python!", actualPost.get().getComments().get(1).getText());
        assertEquals("This is JavaScript!", actualPost.get().getComments().get(2).getText());
    }
}