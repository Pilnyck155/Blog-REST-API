package com.pilnyck.blogrestapi.service;

import com.pilnyck.blogrestapi.dto.PostWithCommentsDto;
import com.pilnyck.blogrestapi.dto.PostWithoutCommentDto;
import com.pilnyck.blogrestapi.dto.TagWithoutPostsDto;
import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.entity.Tag;
import com.pilnyck.blogrestapi.repository.PostRepository;
import com.pilnyck.blogrestapi.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DefaultTagService implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PostRepository postRepository;

    //@Transactional
    @Override
    public void saveTagByPostId(Tag tag, long postId) {
//        Post postFromDB = postRepository.getById(postId);
//        tag.setPosts(Set.of(postFromDB));
//        tagRepository.save(tag);

        Post postFromDB = postRepository.getById(postId);
        //postFromDB.setTags(Set.of(tag));

        tagRepository.save(tag);
        ///postFromDB.getTags().add(tag);
        Set<Tag> postFromDBTags = postFromDB.getTags();
        postFromDBTags.add(tag);
        //postFromDB.setTags(Set.of(tag));
        //postRepository.save(postFromDB);
    }

    @Override
    public List<TagWithoutPostsDto> findAllTags() {
        List<Tag> all = tagRepository.findAll();
        List<TagWithoutPostsDto> listOfTags = new ArrayList<>();
        for (Tag tag : all) {
            listOfTags.add(toTagWithoutPostsDto(tag));
        }
        return listOfTags;
    }

    @Override
    public void deleteTagById(long tagId) {
        tagRepository.deleteById(tagId);
    }

//    @Override
//    public List<PostWithoutCommentDto> findAllPostsByTag(String tagName) {
//        List<Post> postsFromDB = tagRepository.findByTag(tagName);
//        List<PostWithoutCommentDto> listPostWithoutCommentsDTO = new ArrayList<>();
//        for (Post post : postsFromDB){
//            listPostWithoutCommentsDTO.add(toPostWithoutCommentDto(post));
//
//        }
//
//        return listPostWithoutCommentsDTO;
//    }
//
//    private PostWithoutCommentDto toPostWithoutCommentDto(Post post) {
//        PostWithoutCommentDto postWithoutCommentDto = new PostWithoutCommentDto();
//        postWithoutCommentDto.setPostId(post.getPostId());
//        postWithoutCommentDto.setContent(post.getContent());
//        postWithoutCommentDto.setTitle(post.getTitle());
//        postWithoutCommentDto.setStar(post.isStar());
//        return null;
//    }

    private TagWithoutPostsDto toTagWithoutPostsDto(Tag tag) {
        TagWithoutPostsDto tagWithoutPostsDto = new TagWithoutPostsDto();
        tagWithoutPostsDto.setTagId(tag.getTagId());
        tagWithoutPostsDto.setTagName(tag.getTagName());
        return tagWithoutPostsDto;
    }
}
