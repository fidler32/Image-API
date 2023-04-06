package com.example.imageobjectapi.controller;

import com.example.imageobjectapi.document.ImageDocument;
import com.example.imageobjectapi.dto.request.ImageProcessRequest;
import com.example.imageobjectapi.service.googleVision.VisionService;
import com.example.imageobjectapi.service.image.ImageService;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.LocalizedObjectAnnotation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    private final VisionService visionService;

    @GetMapping(path = {"/images"})
    public Flux<ImageDocument> getImages(@RequestParam(value ="object", required = false) Optional<String> objects){
        if(objects.isPresent()){
            return this.imageService.getAllImagesWithObjects(objects.get());
        }else {
            return this.imageService.getAllImages();
        }
    }

    @GetMapping(path = {"/images/{id}"})
    public Mono<ResponseEntity<ImageDocument>> getImageById(@PathVariable String id){
        return this.imageService
                .getImageById(id)
                .map(image -> ResponseEntity.ok(image))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(path= {"/images"})
    public Mono<ImageDocument> processImage(
             @RequestBody ImageProcessRequest request){
        return imageService.processImage(request);
    }
}
