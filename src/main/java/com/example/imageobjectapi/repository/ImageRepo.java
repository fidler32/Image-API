package com.example.imageobjectapi.repository;

import com.example.imageobjectapi.document.ImageDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface ImageRepo extends ReactiveMongoRepository<ImageDocument, String> {
    Flux<ImageDocument> findAll();
    Mono<ImageDocument> findById(String id);
    Flux<ImageDocument> findByObjectsContainsIgnoreCase(List<String> objectsToFind);
}
