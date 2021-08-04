package com.max.aluraflix.controllers;

import com.max.aluraflix.dto.CategoryInsert;
import com.max.aluraflix.dto.CategoryView;
import com.max.aluraflix.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("categories")
public class CategoryController {

    @Autowired
    private CategoryService service;


    @GetMapping
    public ResponseEntity<Page<CategoryView>> findAllPaged(Pageable pageable) {
        return ResponseEntity.ok().body(service.findAllPaged(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryView> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryView> insert(@Valid @RequestBody CategoryInsert dto) {
        CategoryView view = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/id").buildAndExpand(view.getId()).toUri();
        return ResponseEntity.created(uri).body(view);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryView> update(@PathVariable Long id, @RequestBody CategoryInsert dto) {
        CategoryView view = service.update(id, dto);
        return ResponseEntity.ok(view);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
