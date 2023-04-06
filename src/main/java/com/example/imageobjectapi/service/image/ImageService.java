package com.example.imageobjectapi.service.image;

import com.example.imageobjectapi.document.ImageDocument;
import com.example.imageobjectapi.dto.request.ImageProcessRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface ImageService {
    Flux<ImageDocument> getAllImages();
    Flux<ImageDocument> getAllImagesWithObjects(String objects);

    Mono<ImageDocument> getImageById(String id);

    Mono<ImageDocument> processImage(ImageProcessRequest request);
}
