package com.app.twitter.services;

import com.app.twitter.entities.dtos.comment.AddCommentDTO;
import com.app.twitter.entities.dtos.comment.CommentResponseDTO;
import com.app.twitter.entities.dtos.comment.UpdateCommentDTO;
import com.app.twitter.entities.dtos.error.ErrResponse;
import com.app.twitter.entities.Comment;
import com.app.twitter.entities.Post;
import com.app.twitter.entities.User;
import com.app.twitter.repositories.CommentRepository;
import com.app.twitter.repositories.PostRepository;
import com.app.twitter.repositories.UserRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> fetchAllComments() {
        return new ResponseEntity<>(commentRepository.findAll(), HttpStatusCode.valueOf(200));
    }

    public ResponseEntity<?> updateExistingComment(UpdateCommentDTO updateCommentDTO) {
        Optional<Comment> comment = commentRepository.findById(updateCommentDTO.getCommentID());
        if (comment.isEmpty()) {
            return ResponseEntity.ok(new ErrResponse("Comment does not exist"));
        }
        comment.get().setCommentBody(updateCommentDTO.getCommentBody());
        commentRepository.save(comment.get());
        return ResponseEntity.ok("Comment edited successfully");
    }

    public ResponseEntity<?> doComment(AddCommentDTO addCommentDTO) {
        Optional<User> user = userRepository.findById(addCommentDTO.getUserID());
        if (user.isEmpty()) {
            return ResponseEntity.ok(new ErrResponse("User does not exist"));
        } else {
            Optional<Post> post = postRepository.findById(addCommentDTO.getPostID());
            if (post.isEmpty()) {
                return ResponseEntity.ok(new ErrResponse("Post does not exist"));
            } else {
                Comment comment = new Comment();
                comment.setCommentBody(addCommentDTO.getCommentBody());
                comment.setCommentCreator(user.get());
                Comment savedComment = commentRepository.save(comment);
                post.get().getComments().add(savedComment);
                postRepository.save(post.get());
                return ResponseEntity.ok("Comment created successfully");
            }
        }
    }

    public ResponseEntity<?> deleteComment(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()) {
            return ResponseEntity.ok(new ErrResponse("Comment does not exist"));
        } else {
            commentRepository.deleteById(id);
            return ResponseEntity.ok("Comment deleted successfully");
        }
    }

    public ResponseEntity<?> findCommentByItsId(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()) {
            return ResponseEntity.ok(new ErrResponse("Comment does not exist"));
        } else {
            CommentResponseDTO commentResponseDto = new CommentResponseDTO();
            commentResponseDto.setCommentID(comment.get().getCommentID());
            commentResponseDto.setCommentBody(comment.get().getCommentBody());
            commentResponseDto.setCommentCreator(comment.get().getCommentCreator());
            return ResponseEntity.ok(commentResponseDto);
        }
    }


}
