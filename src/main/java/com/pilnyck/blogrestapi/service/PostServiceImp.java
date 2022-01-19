package com.pilnyck.blogrestapi.service;

import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PostServiceImp implements PostService{
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getById(long id) {
        return postRepository.findById(id).get();
    }

    @Override
    public void deletePostById(long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Post editPostById(Post post, long id) {
        //TODO: Rewrite method and take one query to DB
        Post postFromDB = postRepository.findById(id).get();
        if (Objects.nonNull(post.getTitle()) && !"".equalsIgnoreCase(post.getTitle())){
            postFromDB.setTitle(post.getTitle());
        }
        if (Objects.nonNull(post.getContent()) && !"".equalsIgnoreCase(post.getContent())){
            postFromDB.setContent(post.getContent());
        }
        return postRepository.save(postFromDB);
    }

    @Override
    public List<Post> findAllPostsByTitle(String title) {
        return postRepository.findAllByTitle(title);
    }

    @Override
    public List<Post> findAllPostsSortedByTitle() {
        logger.info("int findAllPostsSortedByTitle title");
        return postRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
        //return postRepository.findAllPostsOrderByTitleAsc(sort);
    }
}
