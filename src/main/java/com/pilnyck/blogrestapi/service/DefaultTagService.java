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

    private final TagRepository tagRepository;

    private final PostRepository postRepository;

    public DefaultTagService(TagRepository tagRepository, PostRepository postRepository) {
        this.tagRepository = tagRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void saveTagByPostId(Tag tag, long postId) {
        Post postFromDB = postRepository.getById(postId);

        postFromDB.getTags().add(tag);
        tag.getPosts().add(postFromDB);

        tagRepository.save(tag);
        postRepository.save(postFromDB);
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

    private TagWithoutPostsDto toTagWithoutPostsDto(Tag tag) {
        TagWithoutPostsDto tagWithoutPostsDto = new TagWithoutPostsDto();
        tagWithoutPostsDto.setTagId(tag.getTagId());
        tagWithoutPostsDto.setTagName(tag.getTagName());
        return tagWithoutPostsDto;
    }
}
