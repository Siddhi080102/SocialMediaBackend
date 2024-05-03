package com.app.twitter.entities.dtos.comment;

public class CommenterDTO {
    private String name;
    private String email;

    public CommenterDTO() {
    }

    public CommenterDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
