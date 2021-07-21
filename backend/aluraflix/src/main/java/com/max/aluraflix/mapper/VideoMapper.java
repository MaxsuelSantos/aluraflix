package com.max.aluraflix.mapper;

import com.max.aluraflix.dto.VideoDTO;
import com.max.aluraflix.entities.Video;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper {

    public Video toEntity(VideoDTO dto) {
        Video entity = new Video();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setUrl(dto.getUrl());
        return entity;
    }

    public VideoDTO toDTO(Video entity) {
        VideoDTO dto = new VideoDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setUrl(entity.getUrl());
        return dto;
    }

    public Page<VideoDTO> toDTO(Page<Video> pageVideo) {
        return pageVideo.map(this::toDTO);
    }

    public Video updateEntity(Video entity, VideoDTO dto) {
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setUrl(dto.getUrl());
        return entity;
    }

}
