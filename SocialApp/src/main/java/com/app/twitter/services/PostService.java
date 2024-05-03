package com.app.twitter.services;

import com.app.twitter.entities.dtos.comment.CommentResponseDTO;
import com.app.twitter.entities.dtos.error.ErrResponse;
import com.app.twitter.entities.dtos.post.CreatePostDTO;
import com.app.twitter.entities.dtos.post.PostResponseDTO;
import com.app.twitter.entities.dtos.post.UpdatePostDto;
import com.app.twitter.entities.Comment;
import com.app.twitter.entities.Post;
import com.app.twitter.entities.User;
import com.app.twitter.repositories.PostRepository;
import com.app.twitter.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByDateDesc();
    }

    public ResponseEntity<?> savePost(CreatePostDTO createPostDTO) {
        Optional<User> user = userRepository.findById(createPostDTO.getUserID());
        if (user.isEmpty()) {
            return ResponseEntity.ok(new ErrResponse("User does not exist"));
        }
        Post post = new Post();
        post.setPostBody(createPostDTO.getPostBody());
        post.setDate(LocalDate.now());
        postRepository.save(post);

        if (user.get().getPosts() == null) {
            user.get().setPosts(new ArrayList<>());
        }
        user.get().getPosts().add(post);
        userRepository.save(user.get());
        return ResponseEntity.ok("Post created successfully");
    }


    public ResponseEntity<?> getPostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            return ResponseEntity.ok(new ErrResponse("Post does not exist"));
        }
        PostResponseDTO postResponseDto = new PostResponseDTO();
        postResponseDto.setPostBody(post.get().getPostBody());
        postResponseDto.setPostID(post.get().getPostID());
        postResponseDto.setDate(post.get().getDate());
        for (Comment comment : post.get().getComments()) {
            CommentResponseDTO commentResponseDto = new CommentResponseDTO();
            commentResponseDto.setCommentBody(comment.getCommentBody());
            commentResponseDto.setCommentID(comment.getCommentID());
            commentResponseDto.setCommentCreator(comment.getCommentCreator());
        }
        return ResponseEntity.ok(postResponseDto);
    }

    public ResponseEntity<?> updatePost(UpdatePostDto updatePostDto) {
        Optional<Post> post = postRepository.findById(updatePostDto.getPostID());
        if (post.isEmpty()) {
            return ResponseEntity.ok(new ErrResponse("Post does not exist"));
        }
        post.get().setPostBody(updatePostDto.getPostBody());
        postRepository.save(post.get());
        return ResponseEntity.ok("Post edited successfully");
    }


    public ResponseEntity<?> deletePost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            return ResponseEntity.ok(new ErrResponse("Post does not exist"));
        }
        postRepository.delete(post.get());
        return ResponseEntity.ok("Post deleted");
    }

}
