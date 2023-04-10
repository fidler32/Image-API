package com.example.imageobjectapi.repository;

import com.example.imageobjectapi.document.ImageDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepo extends MongoRepository<ImageDocument, String> {
    Optional<ImageDocument> findById(String id);
    List<ImageDocument> findByObjectsContainsIgnoreCase(List<String> objectsToFind);
}
