package com.max.aluraflix.tests;

import com.max.aluraflix.dto.VideoDTO;
import com.max.aluraflix.entities.Video;

public class Factory {

    public static Video createVideo() {
        return new Video(1L, "Comece a trabalhar sem experiência", "Código Fonte TV", "https://www.youtube.com/watch?v=llBJb_QT6ho");
    }

    public static VideoDTO createVideoDTO() {
        return new VideoDTO(1L, "Comece a trabalhar sem experiência", "Código Fonte TV", "https://www.youtube.com/watch?v=llBJb_QT6ho", 1L);
    }

}
