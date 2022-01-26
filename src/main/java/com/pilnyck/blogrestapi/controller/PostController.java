package com.pilnyck.blogrestapi.controller;

import com.pilnyck.blogrestapi.dto.CommentWithPostDto;
import com.pilnyck.blogrestapi.dto.CommentWithoutPostDto;
import com.pilnyck.blogrestapi.dto.PostWithCommentsDto;
import com.pilnyck.blogrestapi.dto.PostWithoutCommentDto;
import com.pilnyck.blogrestapi.entity.Comment;
import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PostService postService;

    @PostMapping
    public Post savePost(@RequestBody Post post) {
        return postService.savePost(post);
    }

    @GetMapping
    public List<Post> getAllPosts(@RequestParam(value = "title", required = false) String title,
                                  @RequestParam(value = "sort", required = false) String sort) {
        logger.info("getAllPostsMethod");
        if (title != null) {
            logger.info("in findAllPostsByTitle method");
            return postService.findAllPostsByTitle(title);
        } else if (sort != null) {
            logger.info("in findAllPostsSortedByTitle method");
            return postService.findAllPostsSortedByTitle();
        } else {
            return postService.getAllPosts();
        }
    }

    @GetMapping("/{id}")
    public Post getById(@PathVariable("id") long id) {
        logger.info("getById");
        return postService.getById(id);
    }

    @PutMapping("/{postId}")
    public Post editPostById(@RequestBody Post post, @PathVariable long postId) {
        logger.info("editPostById");
        return postService.editPostById(post, postId);
    }

    @DeleteMapping("/{id}")
    public String deletePostById(@PathVariable("id") long id) {
        logger.info("deletePostById");
        // TODO: Change method to void
        postService.deletePostById(id);
        return "Post delete sucсessful";
    }

    @GetMapping("/star")
    public List<Post> getAllPostsWithStar() {
        logger.info("getAllPostsWithStar");
        return postService.getAllPostsWithStar();
    }

    @PutMapping("/{id}/star")
    public Post addStarToPost(@PathVariable("id") long id) {
        logger.info("addStarToPost");
        return postService.addStarToPost(id);
    }

    @DeleteMapping("/{id}/star")
    public Post deleteStarFromPost(@PathVariable("id") long id) {
        logger.info("deleteStarFromPost");
        return postService.deleteStarFromPost(id);
    }

    @GetMapping("/{id}/full")
    public PostWithCommentsDto getPostWithAllComments(@PathVariable("id") long postId) {
        Optional<Post> postFromDb = postService.getPostWithAllComments(postId);
        if (!postFromDb.isPresent()) {
            throw new IllegalArgumentException("Post with current id doesn't exist");
        }
        Post post = postFromDb.get();
        PostWithCommentsDto postWithCommentsDto = toPostWithCommentsDto(post);
        return postWithCommentsDto;
    }


    private PostWithCommentsDto toPostWithCommentsDto(Post post) {
        PostWithCommentsDto postWithCommentsDto = new PostWithCommentsDto();
        postWithCommentsDto.setPostId(post.getPostId());
        postWithCommentsDto.setContent(post.getContent());
        postWithCommentsDto.setTitle(post.getTitle());
        postWithCommentsDto.setStar(post.isStar());

        List<Comment> commentList = post.getComments();

        List<CommentWithoutPostDto> listComments = new ArrayList<>();
        for (Comment comment : commentList) {
            listComments.add(toCommentWithoutPostDto(comment));
        }
        postWithCommentsDto.setComments(listComments);
        return postWithCommentsDto;
    }

    private CommentWithoutPostDto toCommentWithoutPostDto(Comment comment) {
        CommentWithoutPostDto commentWithoutPostDto = new CommentWithoutPostDto();
        commentWithoutPostDto.setCommentId(comment.getCommentId());
        commentWithoutPostDto.setCreationDate(comment.getCreationDate());
        commentWithoutPostDto.setText(comment.getText());
        return commentWithoutPostDto;
    }
}
