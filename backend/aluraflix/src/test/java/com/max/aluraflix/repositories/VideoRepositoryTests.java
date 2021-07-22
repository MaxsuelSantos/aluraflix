package com.max.aluraflix.repositories;

import com.max.aluraflix.entities.Video;
import com.max.aluraflix.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;


import java.util.Optional;

@DataJpaTest
public class VideoRepositoryTests {

    @Autowired
    private VideoRepository repository;

    private long existingId;
    private long nonExistingId;
    private long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalProducts = 3;
    }

    @Test
    public void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {
        Optional<Video> result = repository.findById(existingId);

        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExists() {
        Optional<Video> result = repository.findById(nonExistingId);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdNull() {
        Video entity = Factory.createVideo();
        entity.setId(null);

        entity = repository.save(entity);

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals(countTotalProducts + 1, entity.getId());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {

        repository.deleteById(existingId);

        Optional<Video> result = repository.findById(existingId);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExists() {

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(nonExistingId);
        });

    }

}
