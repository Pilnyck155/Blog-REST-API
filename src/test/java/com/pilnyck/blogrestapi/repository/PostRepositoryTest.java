package com.pilnyck.blogrestapi.repository;

import com.pilnyck.blogrestapi.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        Post firstPost = Post.builder()
                .title("Fresh news")
                .content("Fresh stupid news")
                .build();
        Post secondPost = Post.builder()
                .postId(2L)
                .title("Very fresh news")
                .content("Very fresh stupid news")
                .build();
        testEntityManager.persist(firstPost);
    }

    @Test
    public void whenFindById_thenReturnPost(){
    }
}