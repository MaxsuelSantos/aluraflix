package com.max.aluraflix.mapper;

import com.max.aluraflix.dto.VideoDTO;
import com.max.aluraflix.entities.Video;
import com.max.aluraflix.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper {

    @Autowired
    private CategoryRepository categoryRepository;

    public Video toEntity(VideoDTO dto) {
        Video entity = new Video();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setUrl(dto.getUrl());
        entity.setCategory(categoryRepository.getOne(dto.getCategoryId()));
        return entity;
    }

    public VideoDTO toDTO(Video entity) {
        VideoDTO dto = new VideoDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setUrl(entity.getUrl());
        dto.setCategoryId(entity.getCategory().getId());
        return dto;
    }

    public Page<VideoDTO> toDTO(Page<Video> pageVideo) {
        return pageVideo.map(this::toDTO);
    }

    public Video updateEntity(Video entity, VideoDTO dto) {
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setUrl(dto.getUrl());
        entity.setCategory(categoryRepository.getOne(dto.getCategoryId()));
        return entity;
    }

}
