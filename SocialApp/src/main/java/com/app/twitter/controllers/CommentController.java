package com.app.twitter.controllers;

import com.app.twitter.entities.dtos.comment.AddCommentDTO;
import com.app.twitter.entities.dtos.comment.UpdateCommentDTO;
import com.app.twitter.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comment")
    public ResponseEntity<?> doComment(@RequestBody AddCommentDTO addCommentDTO) {
        return commentService.doComment(addCommentDTO);
    }

    @GetMapping("/comments")
    public ResponseEntity<?> fetchAllComments() {
        return commentService.fetchAllComments();
    }

    @GetMapping("/comment")
    public ResponseEntity<?> findCommentByItsId(@RequestParam Long id) {
        return commentService.findCommentByItsId(id);
    }


    @PatchMapping("/comment")
    public ResponseEntity<?> updateExistingComment(@RequestBody UpdateCommentDTO updateCommentDTO) {
        return commentService.updateExistingComment(updateCommentDTO);
    }

    @DeleteMapping("/comment")
    public ResponseEntity<?> deleteComment(@RequestParam Long id) {
        return commentService.deleteComment(id);
    }
}
