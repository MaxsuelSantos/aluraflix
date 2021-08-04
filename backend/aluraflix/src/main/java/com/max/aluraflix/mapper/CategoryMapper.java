package com.max.aluraflix.mapper;

import com.max.aluraflix.dto.CategoryInsert;
import com.max.aluraflix.dto.CategoryView;
import com.max.aluraflix.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryInsert dto) {
        Category entity = new Category();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setColor(dto.getColor());
        return entity;
    }

    public CategoryView toDTO(Category entity) {
        CategoryView dto = new CategoryView();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setColor(entity.getColor());
        return dto;
    }

    public Page<CategoryView> toDTO(Page<Category> pageCategory) {
        return pageCategory.map(this::toDTO);
    }

    public Category updateEntity(Category entity, CategoryInsert dto) {
        entity.setTitle(dto.getTitle());
        entity.setColor(dto.getColor());
        return entity;
    }

}
