package com.app.twitter.controllers;

import com.app.twitter.entities.dtos.post.CreatePostDTO;
import com.app.twitter.entities.dtos.post.UpdatePostDto;
import com.app.twitter.entities.Post;
import com.app.twitter.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping("/post")
    public ResponseEntity<?> savePost(@RequestBody CreatePostDTO createPostDTO) {
        return postService.savePost(createPostDTO);
    }

    @PatchMapping("/post")
    public ResponseEntity<?> updatePost(@RequestBody UpdatePostDto updatePostDto) {
        return postService.updatePost(updatePostDto);
    }

    @GetMapping("/post")
    public ResponseEntity<?> getPostById(@RequestParam Long id) {
        return postService.getPostById(id);
    }

    @DeleteMapping("/post")
    public ResponseEntity<?> deletePost(@RequestParam Long id) {
        return postService.deletePost(id);
    }


}
