package com.max.aluraflix.services;

import com.max.aluraflix.dto.CategoryInsert;
import com.max.aluraflix.dto.CategoryView;
import com.max.aluraflix.dto.VideoDTO;
import com.max.aluraflix.entities.Category;
import com.max.aluraflix.entities.Video;
import com.max.aluraflix.mapper.CategoryMapper;
import com.max.aluraflix.repositories.CategoryRepository;
import com.max.aluraflix.services.exceptions.DatabaseException;
import com.max.aluraflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private CategoryMapper mapper;


    @Transactional(readOnly = true)
    public Page<CategoryView> findAllPaged(Pageable pageable) {
        return mapper.toDTO(repository.findAll(pageable));
    }

    @Transactional(readOnly = true)
    public CategoryView findById(Long id) {
        Category entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return mapper.toDTO(entity);
    }

    @Transactional(readOnly = true)
    public Page<VideoDTO> findAllVideoPerCategory(Long id) {
        try {
            Category entity = repository.getOne(id);
            List<VideoDTO> videoDTOS = entity.getVideos().stream().map(VideoDTO::new).collect(Collectors.toList());
            return new PageImpl<>(videoDTOS);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Category not found: " + id);
        }
    }

    @Transactional
    public CategoryView insert(CategoryInsert dto) {
        Category entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public CategoryView update(Long id, CategoryInsert dto) {
        try {
            Category entity = repository.getOne(id);
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
