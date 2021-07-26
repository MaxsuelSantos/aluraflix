package com.max.aluraflix.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.max.aluraflix.dto.VideoDTO;
import com.max.aluraflix.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class VideoControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    public void findAllShouldReturnSortedPageWhenSortByName() throws Exception {

        ResultActions result =
                mockMvc.perform(get("/videos?page=0&size=12&sort=title,asc")
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.totalElements").value(countTotalVideos));
        result.andExpect(jsonPath("$.content").exists());
        result.andExpect(jsonPath("$.content[0].title").value("Middleware"));
        result.andExpect(jsonPath("$.content[1].title").value("O QUE É GIT E GITHUB?"));
        result.andExpect(jsonPath("$.content[2].title").value("SQLite"));

    }

    @Test
    public void updateShouldReturnVideoDTOWhenIdExists() throws Exception {

        VideoDTO videoDTO = Factory.createVideoDTO();
        String jsonBody = objectMapper.writeValueAsString(videoDTO);

        String expectedName = videoDTO.getTitle();
        String expectedDescription = videoDTO.getDescription();

        ResultActions result =
                mockMvc.perform(put("/videos/{id}", existingId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(existingId));
        result.andExpect(jsonPath("$.title").value(expectedName));
        result.andExpect(jsonPath("$.description").value(expectedDescription));

    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {

        VideoDTO videoDTO = Factory.createVideoDTO();
        String jsonBody = objectMapper.writeValueAsString(videoDTO);

        ResultActions result =
                mockMvc.perform(put("/products/{id}", nonExistingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());

    }

}
