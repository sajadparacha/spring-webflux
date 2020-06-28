package com.spring5.webflux.controller;

import com.spring5.webflux.domain.Category;
import com.spring5.webflux.repository.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;

@RestController
public class CategoryController {
    CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @GetMapping("/api/v1/categories")
     Flux<Category> getCategories(){
        return categoryRepository.findAll();
    }
    @GetMapping("/api/v1/category/{id}")
    Mono<Category> getCategory(@PathVariable String id){
        return categoryRepository.findById(id);
    }
    @PostMapping("/api/v1/categories")
    Mono<Void> create(@RequestBody Publisher<Category> categoryStream){
        return categoryRepository.saveAll(categoryStream).then();

    }
    @PutMapping("/api/v1/category/{id}")
    Mono<Category> update(@PathVariable String id, @RequestBody Category category){
        category.setId(id);
        return categoryRepository.save(category);
    }

    @PatchMapping("/api/v1/categories/{id}")
    Mono<Category> patch(@PathVariable String id, @RequestBody Category category) {
            Mono<Category> foundCategory = categoryRepository.findById(id);
            return foundCategory
                    .filter(found -> !Objects.equals(found.getDescription(), category.getDescription()))
                    .flatMap(f -> {
                        f.setDescription(category.getDescription());
                        return categoryRepository.save(f);
                    }).switchIfEmpty(foundCategory);
    }
}
