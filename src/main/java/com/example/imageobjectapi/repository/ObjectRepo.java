package com.example.imageobjectapi.repository;

import com.example.imageobjectapi.entity.ImageEntity;
import com.example.imageobjectapi.entity.ObjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ObjectRepo extends JpaRepository<ObjectEntity, String> {
    Set<ObjectEntity> getObjectEntitiesByNameIn(List<String> objects);

}
