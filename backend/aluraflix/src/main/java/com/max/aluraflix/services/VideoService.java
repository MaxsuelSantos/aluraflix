package com.max.aluraflix.services;

import com.max.aluraflix.dto.VideoDTO;
import com.max.aluraflix.entities.Video;
import com.max.aluraflix.mapper.VideoMapper;
import com.max.aluraflix.repositories.VideoRepository;
import com.max.aluraflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityNotFoundException;


@Service
public class VideoService {

    @Autowired
    private VideoRepository repository;

    @Autowired
    private VideoMapper mapper;


    @Transactional(readOnly = true)
    public Page<VideoDTO> findAllPaged(Pageable pageable) {
        return mapper.toDTO(repository.findAll(pageable));
    }

    @Transactional(readOnly = true)
    public VideoDTO findById(Long id) {
        Video entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return mapper.toDTO(entity);
    }

    @Transactional
    public VideoDTO insert(VideoDTO dto) {
        Video entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public VideoDTO update(Long id, VideoDTO dto) {
        try {
            Video entity = repository.getOne(id);
            mapper.updateEntity(entity, dto);
            entity = repository.save(entity);
            return mapper.toDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }
    }

}
