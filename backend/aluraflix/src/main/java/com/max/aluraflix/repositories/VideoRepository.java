package com.max.aluraflix.repositories;

import com.max.aluraflix.entities.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VideoRepository extends JpaRepository<Video, Long> {

    @Query("SELECT obj FROM Video obj WHERE " +
            "LOWER(obj.title) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Video> findAll(String search, Pageable pageable);

}
