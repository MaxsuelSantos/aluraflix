package com.max.aluraflix.services;

import com.max.aluraflix.dto.VideoDTO;
import com.max.aluraflix.repositories.VideoRepository;
import com.max.aluraflix.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class VideoServiceTI {

    @Autowired
    private VideoService service;

    @Autowired
    private VideoRepository repository;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalVideos;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalVideos = 3L;
    }


    @Test
    public void findAllPagedShouldReturnSortedPageWhenSortByName() {

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("title"));

        Page<VideoDTO> result = service.findAllPaged(pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("Middleware", result.getContent().get(0).getTitle());
        Assertions.assertEquals("O QUE É GIT E GITHUB?", result.getContent().get(1).getTitle());
        Assertions.assertEquals("SQLite", result.getContent().get(2).getTitle());

    }

    @Test
    public void findAllPagedShouldReturnPageWhenPage0Size10() {

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<VideoDTO> result = service.findAllPaged(pageRequest);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(0, result.getNumber());
        Assertions.assertEquals(10, result.getSize());
        Assertions.assertEquals(countTotalVideos, result.getTotalElements());

    }

    @Test
    public void findAllPagedShouldReturnEmptyPageWhenPageDoesNotExist() {

        PageRequest pageRequest = PageRequest.of(50, 10);

        Page<VideoDTO> result = service.findAllPaged(pageRequest);

        Assertions.assertTrue(result.isEmpty());

    }

    @Test
    public void insertShouldPersistWithAutoincrementWhenIdNull() {

        VideoDTO dto = new VideoDTO();
        dto.setId(null);

        VideoDTO result = service.insert(dto);

        Assertions.assertEquals(countTotalVideos + 1, result.getId());
    }

    @Test
    public void deleteShouldDeleteResourceWhenIdExists() {

        service.delete(existingId);

        Assertions.assertEquals(countTotalVideos - 1, repository.count());

    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });

    }

}
