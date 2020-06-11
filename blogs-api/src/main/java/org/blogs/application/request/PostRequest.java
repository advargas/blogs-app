package org.blogs.application.request;

import javax.validation.constraints.NotBlank;

public class PostRequest {

    @NotBlank
    private String title;

    private String image;

    @NotBlank
    private String content;

    public PostRequest(String title, String image, String content) {
        this.title = title;
        this.image = image;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
