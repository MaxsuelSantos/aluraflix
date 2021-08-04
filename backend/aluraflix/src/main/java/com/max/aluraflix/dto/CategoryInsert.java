package com.max.aluraflix.dto;


import com.max.aluraflix.entities.Category;

import java.util.ArrayList;
import java.util.List;


public class CategoryInsert {

    private Long id;

    private String title;

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
