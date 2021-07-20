package com.max.aluraflix.repositories;

import com.max.aluraflix.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
}
