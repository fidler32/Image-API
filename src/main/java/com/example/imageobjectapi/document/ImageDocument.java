package com.example.imageobjectapi.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Document
@ToString
public class ImageDocument {
    @Id
    private String id;
    private String label;
    private List<String> objects;
    private Object metadata;
    private String image;
}
