package com.pilnyck.blogrestapi.controller;

import com.pilnyck.blogrestapi.dto.*;
import com.pilnyck.blogrestapi.entity.Comment;
import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.entity.Tag;
import com.pilnyck.blogrestapi.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PostService postService;

    //TODO: Change from Post to PostWithCommentsDto
    @PostMapping
    public PostWithCommentsDto savePost(@RequestBody Post post) {
        Post savePost = postService.savePost(post);
        PostWithCommentsDto postWithCommentsDto = toPostWithCommentsDto(savePost);
        return postWithCommentsDto;
    }

    //TODO: Change from Post to PostWithCommentsDto
    @GetMapping
    public List<Post> getAllPosts(@RequestParam(value = "title", required = false) String title,
                                  @RequestParam(value = "sort", required = false) String sort) {
        if (title != null) {
            logger.info("Obtain all posts by title {}", title);
            return postService.findAllPostsByTitle(title);
        } else if (sort != null) {
            logger.info("Obtain all sorted posts by sort {}", sort);
            return postService.findAllPostsSortedByTitle();
        } else {
            return postService.getAllPosts();
        }
    }

    //TODO: Change from Post to PostWithCommentsDto
    @GetMapping("/{id}")
    public Post getById(@PathVariable long id) {
        logger.info("Obtain post by id {}", id);
        return postService.getById(id);
    }

    //TODO: Change from Post to PostWithCommentsDto
    @PutMapping("/{postId}")
    public PostWithCommentsDto editPostById(@RequestBody Post post, @PathVariable long postId) {
        logger.info("Change post by id {}", postId);
        Post editedPost = postService.editPostById(post, postId);
        PostWithCommentsDto postWithCommentsDto = toPostWithCommentsDto(editedPost);
        return postWithCommentsDto;
    }

    @DeleteMapping("/{id}")
    public String deletePostById(@PathVariable long id) {
        // TODO: Change method to void
        logger.info("Delete post by id {}", id);
        postService.deletePostById(id);
        return "Post delete successfully";
    }

    //TODO: Change from Post to PostWithCommentsDto
    @GetMapping("/star")
    public List<Post> getAllPostsWithStar() {
        logger.info("Obtain all posts with star");
        return postService.getAllPostsWithStar();
    }

    //TODO: Change from Post to PostWithCommentsDto
    @PutMapping("/{id}/star")
    public Post addStarToPost(@PathVariable long id) {
        logger.info("Add star to  post by id {}", id);
        return postService.addStarToPost(id);
    }

    //TODO: Change from Post to PostWithCommentsDto
    @DeleteMapping("/{id}/star")
    public Post deleteStarFromPost(@PathVariable long id) {
        logger.info("Delete star from post by id {}", id);
        return postService.deleteStarFromPost(id);
    }

    @GetMapping("/{postId}/full")
    public PostWithCommentsDto getPostWithAllComments(@PathVariable long postId) {
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
        if (commentList != null) {
            List<CommentWithoutPostDto> listComments = new ArrayList<>();
            for (Comment comment : commentList) {
                listComments.add(toCommentWithoutPostDto(comment));
            }
            postWithCommentsDto.setComments(listComments);
        }


        Set<TagWithoutPostsDto> tagWithoutPostsDtos = new LinkedHashSet<>();
        Set<Tag> tags = post.getTags();
        for (Tag tag : tags) {
            tagWithoutPostsDtos.add(toTagWithoutPostsDto(tag));
        }
        postWithCommentsDto.setTags(tagWithoutPostsDtos);

        return postWithCommentsDto;
    }

    private CommentWithoutPostDto toCommentWithoutPostDto(Comment comment) {
        CommentWithoutPostDto commentWithoutPostDto = new CommentWithoutPostDto();
        commentWithoutPostDto.setCommentId(comment.getCommentId());
        commentWithoutPostDto.setCreationDate(comment.getCreationDate());
        commentWithoutPostDto.setText(comment.getText());
        return commentWithoutPostDto;
    }

    private TagWithoutPostsDto toTagWithoutPostsDto(Tag tag) {
        TagWithoutPostsDto tagWithoutPostsDto = new TagWithoutPostsDto();
        tagWithoutPostsDto.setTagId(tag.getTagId());
        tagWithoutPostsDto.setTagName(tag.getTagName());
        return tagWithoutPostsDto;
    }
}
