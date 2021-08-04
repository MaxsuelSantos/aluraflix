package com.max.aluraflix.dto;


import com.max.aluraflix.entities.Category;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


public class CategoryInsert {

    private Long id;

    @NotBlank
    @Size(min = 1, max = 100, message = "It must be between 1 and 100 characters long.")
    private String title;

    @NotBlank
    @Size(min = 4, max = 7, message = "It must be between 4 and 7 characters long.")
    private String color;

    private List<VideoDTO> videos = new ArrayList<>();

    public CategoryInsert() {
    }

    public CategoryInsert(Long id, String title, String color) {
        this.id = id;
        this.title = title;
        this.color = color;
    }

    public CategoryInsert(Category entity) {
        id = entity.getId();
        title = entity.getTitle();
        color = entity.getColor();
        entity.getVideos().forEach(x -> this.videos.add(new VideoDTO(x)));
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<VideoDTO> getVideos() {
        return videos;
    }
}
