package com.pilnyck.blogrestapi.service.realization;

import com.pilnyck.blogrestapi.entity.Comment;
import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.repository.CommentRepository;
import com.pilnyck.blogrestapi.repository.PostRepository;
import com.pilnyck.blogrestapi.service.interfaces.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DefaultCommentServiceTest {

    private CommentService commentService;

    private CommentRepository commentRepository;

    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        commentRepository = mock(CommentRepository.class);
        postRepository = mock(PostRepository.class);
        commentService = new DefaultCommentService(commentRepository, postRepository);

        Post post = Post.builder()
                .postId(5L)
                .content("For start lear Java programing language you need learn Indian language")
                .title("Start in Java")
                .star(true)
                .build();
        Comment comment = Comment.builder()
                .commentId(3L)
                .creationDate(LocalDateTime.of(2022, Month.JANUARY, 10, 17, 43, 37, 5))
                .text("This is Java!")
                .post(post)
                .build();
        post.setComments(List.of(comment));
        when(postRepository.getById(anyLong())).thenReturn(post);
        when(commentRepository.findCommentByPostIdAndCommentId(5L, 3L)).thenReturn(comment);
    }

    @Test
    void saveComment() {
        Comment comment = Comment.builder()
                .commentId(1L)
                .creationDate(LocalDateTime.of(2022, Month.JANUARY, 10, 17, 43, 37, 5))
                .text("This is Java!")
                .build();
        when(commentRepository.save(comment)).thenReturn(comment);
        Comment actualComment = commentService.saveComment(5L, comment);
        assertEquals(comment.getCommentId(), actualComment.getCommentId());
        assertEquals(comment.getCreationDate(), actualComment.getCreationDate());
        assertEquals(comment.getText(), actualComment.getText());
    }

    @Test
    void getCommentsByPostId() {
        List<Comment> commentsByPostId = commentService.getCommentsByPostId(5L);
        assertEquals(1, commentsByPostId.size());
        assertEquals("This is Java!", commentsByPostId.get(0).getText());
    }

    @Test
    void getCommentByPostIdAndCommentId() {
        Post post = Post.builder()
                .postId(5L)
                .content("For start lear Java programing language you need learn Indian language")
                .title("Start in Java")
                .star(true)
                .build();
        Comment expectedComment = Comment.builder()
                .commentId(3L)
                .creationDate(LocalDateTime.of(2022, Month.JANUARY, 10, 17, 43, 37, 5))
                .text("This is Java!")
                .post(post)
                .build();
        Comment actualComment = commentService.getCommentByPostIdAndCommentId(5L, 3L);
        assertEquals(expectedComment.getCommentId(), actualComment.getCommentId());
        assertEquals(expectedComment.getCreationDate(), actualComment.getCreationDate());
        assertEquals(expectedComment.getText(), actualComment.getText());
        assertEquals(expectedComment.getPost().getPostId(), actualComment.getPost().getPostId());
    }
}