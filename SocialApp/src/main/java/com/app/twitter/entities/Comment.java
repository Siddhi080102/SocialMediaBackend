package com.app.twitter.entities;

import com.app.twitter.entities.dtos.comment.CommenterDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long commentID;
    private String commentBody;
    @ManyToOne(fetch = FetchType.EAGER)
    private User commentCreator;

    public CommenterDTO getCommentCreator() {
        CommenterDTO commenterDTO = new CommenterDTO();
        commenterDTO.setName(this.commentCreator.getName());
        commenterDTO.setEmail(this.commentCreator.getEmail());
        return commenterDTO;
    }

    public Long getCommentID() {
        return commentID;
    }

    public void setCommentID(Long commentID) {
        this.commentID = commentID;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public void setCommentCreator(User commentCreator) {
        this.commentCreator = commentCreator;
    }
}
