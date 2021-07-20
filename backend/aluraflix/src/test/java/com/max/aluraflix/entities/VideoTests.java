package com.max.aluraflix.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VideoTests {

    @Test
    public void videoShouldHaveCorrectStructure() {

        Video entity = new Video();
        entity.setId(1L);
        entity.setTitle("Title");
        entity.setDescription("Description");
        entity.setUrl("url");

        Assertions.assertNotNull(entity.getId());
        Assertions.assertNotNull(entity.getTitle());
        Assertions.assertNotNull(entity.getDescription());
        Assertions.assertNotNull(entity.getUrl());

    }
}
