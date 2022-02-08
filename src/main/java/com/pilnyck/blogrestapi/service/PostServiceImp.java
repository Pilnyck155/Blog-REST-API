package com.pilnyck.blogrestapi.service;

import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.entity.Tag;
import com.pilnyck.blogrestapi.repository.PostRepository;
import com.pilnyck.blogrestapi.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PostServiceImp implements PostService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Post savePost(Post post) {
        Set<Tag> postTags = post.getTags();
        List<Tag> tagList = postTags.stream().toList();

        for (Tag tag : tagList) {
            boolean existsByTagName = tagRepository.existsByTagName(tag.getTagName());
            if (!existsByTagName) {
                Tag savedTag = tagRepository.save(tag);
                tag.setTagId(savedTag.getTagId());
            } else {
                Tag tagFromDB = tagRepository.getByTagName(tag.getTagName());
                tag.setTagId(tagFromDB.getTagId());
            }
        }
        Set<Tag> targetTagsSet = new HashSet<>(tagList);

        post.setTags(targetTagsSet);
        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getById(long id) {
        Optional<Post> byId = postRepository.findById(id);
        if (byId.isEmpty()) {
            throw new IllegalArgumentException("Current post doesn't exist");
        }
        return byId.get();
    }

    @Override
    public void deletePostById(long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Post editPostById(Post post, long postId) {
        post.setPostId(postId);
        return savePost(post);
    }

    @Override
    public List<Post> findAllPostsByTitle(String title) {
        return postRepository.findAllByTitle(title);
    }

    @Override
    public List<Post> findAllPostsSortedByTitle() {
        logger.info("int findAllPostsSortedByTitle title");
        return postRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
    }

    @Override
    public List<Post> getAllPostsWithStar() {
        return postRepository.findAllByStarTrue();
    }

    @Override
    public Post addStarToPost(long id) {
        return postRepository.updatePostByIdAndSetTrue(id);
    }

    @Override
    public Post deleteStarFromPost(long id) {
        return postRepository.updatePostByIdAndSetFalse(id);
    }

    @Override
    public Optional<Post> getPostWithAllComments(long postId) {
        return postRepository.findById(postId);
    }
}
