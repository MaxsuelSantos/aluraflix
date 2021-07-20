package com.max.aluraflix.services;

import com.max.aluraflix.dto.VideoDTO;
import com.max.aluraflix.mapper.VideoMapper;
import com.max.aluraflix.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

}
