package com.pilnyck.blogrestapi.controller;

import com.pilnyck.blogrestapi.entity.Comment;
import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.service.CommentService;
import com.pilnyck.blogrestapi.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PostService postService;

//    @Autowired
//    private CommentService commentService;

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


    @PutMapping("/{id}")
    public Post editPostById(@RequestBody Post post, @PathVariable long id) {
        logger.info("editPostById");
        return postService.editPostById(post, id);
    }


    @DeleteMapping("/{id}")
    public String deletePostById(@PathVariable("id") long id) {
        logger.info("deletePostById");
        // TODO: Change method to void
        postService.deletePostById(id);
        return "Post delete sucсessful";
    }

    @GetMapping("/star")
    public List<Post> getAllPostsWithStar(){
        logger.info("getAllPostsWithStar");
        return postService.getAllPostsWithStar();
    }

    @PutMapping("/{id}/star")
    public Post addStarToPost(@PathVariable("id") long id){
        logger.info("addStarToPost");
        return postService.addStarToPost(id);
    }

    @DeleteMapping("/{id}/star")
    public Post deleteStarFromPost(@PathVariable("id") long id){
        logger.info("deleteStarFromPost");
        return postService.deleteStarFromPost(id);
    }

//    @PostMapping("{id}/comments")
//    public Comment addCommentToPostById(@RequestParam(value = "id", required = false) long id,
//                                        @RequestBody Comment comment) {
//        logger.info("in addCommentToPostById method");
//        //comment.setPostId(id);
//        return commentService.saveComment(id, comment);
//    }
}
