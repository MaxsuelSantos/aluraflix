package com.max.aluraflix.dto;


import com.max.aluraflix.entities.Category;

import java.util.ArrayList;
import java.util.List;


public class CategoryView {

    private Long id;

    private String title;

    private String color;


    public CategoryView() {
    }

    public CategoryView(Long id, String title, String color) {
        this.id = id;
        this.title = title;
        this.color = color;
    }

    public CategoryView(Category entity) {
        id = entity.getId();
        title = entity.getTitle();
        color = entity.getColor();
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



}
