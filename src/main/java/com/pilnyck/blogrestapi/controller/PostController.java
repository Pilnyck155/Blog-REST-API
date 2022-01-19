package com.pilnyck.blogrestapi.controller;

import com.pilnyck.blogrestapi.entity.Post;
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
        return postService.getById(id);
    }


    @PutMapping("/{id}")
    public Post editPostById(@RequestBody Post post, @PathVariable long id) {
        return postService.editPostById(post, id);
    }


    @DeleteMapping("/{id}")
    public String deletePostById(@PathVariable("id") long id) {
        postService.deletePostById(id);
        return "Post delete sucсessful";
    }

//    //TODO: findByTitle
//    @GetMapping("?title={title}")
//    public List<Post> findPostByTitle(@PathVariable("title") String title){
//        List<Post> postByTitle = postService.findPostByTitle(title);
//        logger.info("findPostByTitle {}", postByTitle);
//        return postByTitle;
//    }
//    //TODO: sortByTitle

}
