package com.max.aluraflix.services;

import com.max.aluraflix.dto.VideoDTO;
import com.max.aluraflix.entities.Video;
import com.max.aluraflix.mapper.VideoMapper;
import com.max.aluraflix.repositories.VideoRepository;
import com.max.aluraflix.services.exceptions.DatabaseException;
import com.max.aluraflix.services.exceptions.ResourceNotFoundException;
import com.max.aluraflix.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class VideoServiceTests {

    @InjectMocks
    private VideoService service;

    @Mock
    private VideoRepository repository;

    @Mock
    private VideoMapper mapper;

    private long existingId;
    private long nonExistingId;
    private long dependentId;
    private PageImpl<Video> pageEntity;
    private PageImpl<VideoDTO> pageDTO;
    private Video video;
    private VideoDTO videoDTO;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;
        video = Factory.createVideo();
        videoDTO = Factory.createVideoDTO();
        pageEntity = new PageImpl<>(List.of(video));
        pageDTO = new PageImpl<>((List.of(videoDTO)));

        Mockito.when(repository.findAll((Pageable) any())).thenReturn(pageEntity);

        Mockito.when(repository.save(any())).thenReturn(video);

        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(video));
        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        Mockito.when(repository.getOne(existingId)).thenReturn(video);
        Mockito.when(repository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);

        Mockito.doNothing().when(repository).deleteById(existingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

        Mockito.when(mapper.toDTO(pageEntity)).thenReturn(pageDTO);
        Mockito.when(mapper.toDTO(video)).thenReturn(videoDTO);
        Mockito.when(mapper.updateEntity(video, videoDTO)).thenReturn(video);
    }


    @Test
    public void findAllPagedShouldReturnPage() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<VideoDTO> result = service.findAllPaged(pageable);

        Assertions.assertNotNull(result);

    }

    @Test
    public void findByIdShouldReturnVideoDTOWhenIdExists() {

        VideoDTO result = service.findById(existingId);

        Assertions.assertNotNull(result);

    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });

    }

    @Test
    public void saveShouldReturnVideoDTO() {

        VideoDTO dto = new VideoDTO();
        dto.setId(null);

        VideoDTO result = service.insert(dto);

        Assertions.assertNotNull(result);

    }

    @Test
    public void updateShouldReturnVideoDTOWhenIdExists() {

        VideoDTO result = service.update(existingId, videoDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(existingId, result.getId());

    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistingId, videoDTO);
        });

    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {

        Assertions.assertDoesNotThrow(() -> {
            service.delete(existingId);
        });

        Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });

        Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);

    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenIdDoesNotExists() {

        Assertions.assertThrows(DatabaseException.class, () -> {
            service.delete(dependentId);
        });

        Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);

    }
}
