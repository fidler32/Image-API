package com.example.imageobjectapi.service.image;

import com.example.imageobjectapi.document.ImageDocument;
import com.example.imageobjectapi.dto.request.ImageProcessRequest;
import com.example.imageobjectapi.repository.ImageRepo;
import com.example.imageobjectapi.service.googleVision.VisionService;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepo imageRepo;
    private final VisionService visionService;
    @Override
    public Flux<ImageDocument> getAllImages() {
        return this.imageRepo.findAll();
    }
    @Override
    public Flux<ImageDocument> getAllImagesWithObjects(String objects) {
        return this.imageRepo.findByObjectsContainsIgnoreCase(Arrays.asList(objects.split(",")));
    }
    @Override
    public Mono<ImageDocument> getImageById(String id) {
        return imageRepo.findById(id);
    }

//    @Override
//    public Mono<ImageDocument> processImage(ImageProcessRequest request) {
//
//        AnnotateImageResponse response = visionService.analyzeImage(request.getUrl());
//        List<String> objects = response.getLocalizedObjectAnnotationsList().stream()
//                .map(item -> item.getName())
//                .distinct()
//                .collect(Collectors.toList());
//
//        return imageRepo.save(ImageDocument.builder()
//                .label("label")
//                .metadata(response.getLocalizedObjectAnnotationsList().toString())
//                .objects(objects)
//                        .image(request.getUrl())
//                .build()
//        );
//    }

    @Override
    public Mono<ImageDocument> processImage(ImageProcessRequest request) {

        AnnotateImageResponse response = visionService.analyzeImage(request.getUrl());
        List<String> objects = response.getLocalizedObjectAnnotationsList().stream()
                .map(item -> item.getName())
                .distinct()
                .collect(Collectors.toList());

        return imageRepo.save(ImageDocument.builder()
                .label("label")
                .metadata(response.getLocalizedObjectAnnotationsList().toString())
                .objects(objects)
                .image(request.getUrl())
                .build()
        );
    }
}
