package com.max.aluraflix.dto;

import com.max.aluraflix.entities.Video;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class VideoDTO {

    private Long id;

    @Size(min = 5, max = 50, message = "It must be between 5 and 50 characters long.")
    @NotBlank
    private String title;

    private Long categoryId;

    @Size(min = 10, max = 280, message = "It must be between 10 and 280 characters long.")
    @NotBlank
    private String description;

    @URL(message = "Must be a valid URL")
    private String url;

    public VideoDTO() {
    }

    public VideoDTO(Long id, String title, String description, String url) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public VideoDTO(Long id, String title, String description, String url, Long categoryId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
        this.categoryId = categoryId;
    }

    public VideoDTO(Video entity) {
        id = entity.getId();
        title = entity.getTitle();
        description = entity.getDescription();
        url = entity.getUrl();
        categoryId = entity.getCategory().getId();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
