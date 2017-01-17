package com.mbenroberts.models;

import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Title Cannot be empty")
    @Column(nullable = false)
    private String title;

    @NotBlank(message="Your post must have a body")
    @Size(min = 10, message="Your body must have at least 10 characters")
    @Column(nullable = false, columnDefinition = "text")
    private String body;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createAt;

    @Column
    @UpdateTimestamp
    private Timestamp updateAt;

    @ManyToOne
    private User user;

    @Column
    private String imageURL;

    public Post(Long id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public Post(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Timestamp getCreateAt() {
        return this.createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getUpdateAt() {
        return this.updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
