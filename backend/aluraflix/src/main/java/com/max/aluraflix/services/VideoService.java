package com.max.aluraflix.services;

import com.max.aluraflix.dto.VideoDTO;
import com.max.aluraflix.entities.Video;
import com.max.aluraflix.mapper.VideoMapper;
import com.max.aluraflix.repositories.CategoryRepository;
import com.max.aluraflix.repositories.VideoRepository;
import com.max.aluraflix.services.exceptions.DatabaseException;
import com.max.aluraflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;


@Service
public class VideoService {

    @Autowired
    private VideoRepository repository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private VideoMapper mapper;


    @Transactional(readOnly = true)
    public Page<VideoDTO> findAllPaged(String search, Pageable pageable) {
        return mapper.toDTO(repository.findAll(search, pageable));
    }

    @Transactional(readOnly = true)
    public VideoDTO findById(Long id) {
        Video entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return mapper.toDTO(entity);
    }

    @Transactional
    public VideoDTO insert(VideoDTO dto) {
        try {
            if (dto.getCategoryId() == null) {
                dto.setCategoryId(1L);
            }
            Video entity = mapper.toEntity(dto);
            return mapper.toDTO(repository.save(entity));
        } catch (DataIntegrityViolationException e) {
            throw new ResourceNotFoundException("Category not found: " + dto.getCategoryId());
        }
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

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

}
