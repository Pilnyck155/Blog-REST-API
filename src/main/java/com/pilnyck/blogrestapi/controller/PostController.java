package com.pilnyck.blogrestapi.controller;

import com.pilnyck.blogrestapi.dto.CommentWithoutPostDto;
import com.pilnyck.blogrestapi.dto.PostWithCommentsDto;
import com.pilnyck.blogrestapi.dto.PostWithoutCommentDto;
import com.pilnyck.blogrestapi.dto.TagWithoutPostsDto;
import com.pilnyck.blogrestapi.entity.Comment;
import com.pilnyck.blogrestapi.entity.Post;
import com.pilnyck.blogrestapi.entity.Tag;
import com.pilnyck.blogrestapi.service.interfaces.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostController {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public PostWithCommentsDto savePost(@RequestBody Post post) {
        Post savePost = postService.savePost(post);
        PostWithCommentsDto postWithCommentsDto = toPostWithCommentsDto(savePost);
        return postWithCommentsDto;
    }

    @GetMapping
    public List<PostWithoutCommentDto> getAllPosts(@RequestParam(value = "title", required = false) String title,
                                                   @RequestParam(value = "sort", required = false) String sort) {
        if (title != null) {
            logger.info("Obtain all posts by title {}", title);
            List<Post> allPostsByTitle = postService.findAllPostsByTitle(title);
            List<PostWithoutCommentDto> listPostsWithoutCommentsDtoById = toListPostWithoutCommentDto(allPostsByTitle);
            return listPostsWithoutCommentsDtoById;
        } else if (sort != null) {
            logger.info("Obtain all sorted posts by sort {}", sort);
            List<Post> allPostsSortedByTitle = postService.findAllPostsSortedByTitle();
            List<PostWithoutCommentDto> listPostsWithoutCommentsDtoSortedByTitle = toListPostWithoutCommentDto(allPostsSortedByTitle);
            return listPostsWithoutCommentsDtoSortedByTitle;
        } else {
            List<Post> allPosts = postService.getAllPosts();
            List<PostWithoutCommentDto> listPostsWithoutCommentsDTO = toListPostWithoutCommentDto(allPosts);
            return listPostsWithoutCommentsDTO;
        }
    }

    @GetMapping("/{id}")
    public PostWithCommentsDto getById(@PathVariable long id) {
        logger.info("Obtain post by id {}", id);
        Post byId = postService.getById(id);
        PostWithCommentsDto postWithCommentsDtoById = toPostWithCommentsDto(byId);
        return postWithCommentsDtoById;
    }

    @PutMapping("/{postId}")
    public PostWithCommentsDto editPostById(@RequestBody Post post, @PathVariable long postId) {
        logger.info("Change post by id {}", postId);
        Post editedPost = postService.editPostById(post, postId);
        PostWithCommentsDto postWithCommentsDto = toPostWithCommentsDto(editedPost);
        return postWithCommentsDto;
    }

    @DeleteMapping("/{id}")
    public void deletePostById(@PathVariable long id) {
        logger.info("Delete post by id {}", id);
        postService.deletePostById(id);
    }

    @GetMapping("/star")
    public List<PostWithoutCommentDto> getAllPostsWithStar() {
        logger.info("Obtain all posts with star");
        List<Post> allPostsWithStar = postService.getAllPostsWithStar();
        List<PostWithoutCommentDto> listPostsWithoutCommentsDtoWithStar = toListPostWithoutCommentDto(allPostsWithStar);
        return listPostsWithoutCommentsDtoWithStar;
    }

    @PutMapping("/{id}/star")
    public PostWithoutCommentDto addStarToPost(@PathVariable long id) {
        logger.info("Add star to  post by id {}", id);
        Post post = postService.addStarToPost(id);
        PostWithoutCommentDto postWithoutCommentDto = toPostWithoutCommentDto(post);
        return postWithoutCommentDto;
    }

    @DeleteMapping("/{id}/star")
    public PostWithoutCommentDto deleteStarFromPost(@PathVariable long id) {
        logger.info("Delete star from post by id {}", id);
        Post post = postService.deleteStarFromPost(id);
        PostWithoutCommentDto postWithoutCommentDto = toPostWithoutCommentDto(post);
        return postWithoutCommentDto;
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
}
