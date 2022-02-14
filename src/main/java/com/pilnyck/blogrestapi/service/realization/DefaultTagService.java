package com.pilnyck.blogrestapi.service.realization;

import com.pilnyck.blogrestapi.dto.CommentWithoutPostDto;
import com.pilnyck.blogrestapi.dto.PostWithCommentsDto;
import com.pilnyck.blogrestapi.dto.PostWithoutCommentDto;
import com.pilnyck.blogrestapi.dto.TagWithoutPostsDto;
import com.pilnyck.blogrestapi.entity.Comment;
import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.entity.Tag;
import com.pilnyck.blogrestapi.repository.PostRepository;
import com.pilnyck.blogrestapi.repository.TagRepository;
import com.pilnyck.blogrestapi.service.interfaces.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DefaultTagService implements TagService {

    private final TagRepository tagRepository;

    private final PostRepository postRepository;

    @Autowired
    public DefaultTagService(TagRepository tagRepository, PostRepository postRepository) {
        this.tagRepository = tagRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void saveTagByPostId(Tag tag, long postId) {
        Post postFromDB = postRepository.getById(postId);
        Tag tagFromDB = tagRepository.getByTagName(tag.getTagName());
        if (tagFromDB == null) {
            Tag savedTag = tagRepository.save(tag);
            postFromDB.getTags().add(savedTag);
            savedTag.getPosts().add(postFromDB);
        } else {
            Set<Tag> tagsFromPost = postFromDB.getTags();
            Set<Tag> updateSetTags = new HashSet<>(tagsFromPost.size() + 1);
            updateSetTags.add(tagFromDB);
            updateSetTags.addAll(tagsFromPost);
            postFromDB.setTags(updateSetTags);
        }
        postRepository.save(postFromDB);
    }

    //TODO: Rewrite to obtain PostWithCommentsDto
    @Override
    public List<PostWithCommentsDto> findAllPostsByTag(String tagName) {
        List<Post> postList = tagRepository.findByTag(tagName);
        List<PostWithCommentsDto> listPostWithCommentDto = toListPostWithCommentDto(postList);
        return listPostWithCommentDto;
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

    @Override
    public List<PostWithoutCommentDto> findAllPostsByTagsList(List<String> tags) {
        List<Post> postList = tagRepository.findAllPostsByTags(tags);
        List<PostWithoutCommentDto> listPostWithoutCommentDto = toListPostWithoutCommentDto(postList);
        return listPostWithoutCommentDto;
    }

    private TagWithoutPostsDto toTagWithoutPostsDto(Tag tag) {
        TagWithoutPostsDto tagWithoutPostsDto = new TagWithoutPostsDto();
        tagWithoutPostsDto.setTagId(tag.getTagId());
        tagWithoutPostsDto.setTagName(tag.getTagName());
        return tagWithoutPostsDto;
    }

    private PostWithoutCommentDto toPostWithoutCommentDto(Post post) {
        PostWithoutCommentDto postWithoutCommentDto = new PostWithoutCommentDto();
        postWithoutCommentDto.setPostId(post.getPostId());
        postWithoutCommentDto.setContent(post.getContent());
        postWithoutCommentDto.setTitle(post.getTitle());
        postWithoutCommentDto.setStar(post.isStar());
        return postWithoutCommentDto;
    }

    private List<PostWithoutCommentDto> toListPostWithoutCommentDto(List<Post> postList) {
        List<PostWithoutCommentDto> postWithoutCommentsDtoList = new ArrayList<>(postList.size());
        for (Post post : postList) {
            postWithoutCommentsDtoList.add(toPostWithoutCommentDto(post));
        }
        return postWithoutCommentsDtoList;
    }

    //TODO: CHANGE
    private PostWithCommentsDto toPostWithCommentDto(Post post) {
        List<CommentWithoutPostDto> commentWithoutPostDto = new ArrayList<>();
        List<Comment> commentList = post.getComments();
        for (Comment comment : commentList) {
            commentWithoutPostDto.add(toCommentWithoutPostDto(comment));
        }

        Set<TagWithoutPostsDto> tagWithoutPostsDto = new HashSet<>(post.getTags().size());
        Set<Tag> postTags = post.getTags();
        for (Tag tag : postTags) {
            tagWithoutPostsDto.add(toTagWithoutPostsDto(tag));
        }

        PostWithCommentsDto postWithCommentsDto = PostWithCommentsDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .comments(commentWithoutPostDto)
                .content(post.getContent())
                .star(post.isStar())
                .tags(tagWithoutPostsDto)
                .build();

        return postWithCommentsDto;
    }

    //TODO: CHANGE
    private List<PostWithCommentsDto> toListPostWithCommentDto(List<Post> postList) {
        List<PostWithCommentsDto> postWithCommentsDtoList = new ArrayList<>(postList.size());
        for (Post post : postList) {
            postWithCommentsDtoList.add(toPostWithCommentDto(post));
        }
        return postWithCommentsDtoList;
    }

    private CommentWithoutPostDto toCommentWithoutPostDto(Comment comment) {
        CommentWithoutPostDto commentWithoutPostDto = new CommentWithoutPostDto();
        commentWithoutPostDto.setCommentId(comment.getCommentId());
        commentWithoutPostDto.setCreationDate(comment.getCreationDate());
        commentWithoutPostDto.setText(comment.getText());
        return commentWithoutPostDto;
    }
}
