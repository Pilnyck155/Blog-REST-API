package com.pilnyck.blogrestapi.controller;

import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public Post savePost(@RequestBody Post post){
        return postService.savePost(post);
    }

    @GetMapping
    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }

    //TODO: GET_BY_ID method
    @GetMapping("/{id}")
    public Post getById(@PathVariable("id") long id){
        return postService.getById(id);
    }

    //TODO: PUT method
    @PutMapping("/{id}")
    public Post editPostById(@RequestBody Post post, @PathVariable long id){
        return postService.editPostById(post, id);
    }

    //TODO: DELETE method
    @DeleteMapping("/{id}")
    public String deletePostById(@PathVariable("id") long id){
        postService.deletePostById(id);
        return "Post delete sucсessful";
    }

}
