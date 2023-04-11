package com.example.imageobjectapi.entity;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Jacksonized
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "images")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name= "label")
    private String label;
    @Type(ListArrayType.class)
    @Column(name= "objects")
    private List<String> objects;
    @Column(name= "metadata")
    private String metadata;
    @Column(name= "image")
    private String image;
}
