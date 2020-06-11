package org.blogs.infrastructure.entities;

import org.blogs.domain.model.Post;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class PostEntity {

    @Id
    private UUID id;

    @NotNull
    private String user;

    @NotNull
    private String title;

    @NotNull
    private String content;

    private String image;

    public PostEntity() {

    }

    public PostEntity(UUID id, @NotNull String user, @NotNull String title, @NotNull String content, String image) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.image = image;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Post toPost() {
        return new Post(this.id, this.title, this.content, this.image);
    }
}
