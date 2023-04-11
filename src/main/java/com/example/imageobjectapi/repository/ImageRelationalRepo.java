package com.example.imageobjectapi.repository;

import com.example.imageobjectapi.entity.ImageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRelationalRepo extends CrudRepository<ImageEntity, String> {
    List<ImageEntity> findAll();
    Optional<ImageEntity> findById(String id);
    List<ImageEntity> findAllByObjectsIn(List<String> objectsToFind);
}
