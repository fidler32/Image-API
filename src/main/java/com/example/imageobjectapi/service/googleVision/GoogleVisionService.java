package com.example.imageobjectapi.service.googleVision;

import com.google.cloud.spring.vision.CloudVisionTemplate;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.Feature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleVisionService implements VisionService {

    private final CloudVisionTemplate cloudVisionTemplate;
    private final ResourceLoader resourceLoader;

    @Override
    public AnnotateImageResponse analyzeImage(MultipartFile image) {
        AnnotateImageResponse response = cloudVisionTemplate
                .analyzeImage(
                        image.getResource(),
                        Feature.Type.OBJECT_LOCALIZATION
                );
        return response;
    }

    @Override
    public AnnotateImageResponse analyzeImage(String imageUrl) {
        AnnotateImageResponse response = cloudVisionTemplate
                .analyzeImage(
                        this.resourceLoader.getResource(imageUrl),
                        Feature.Type.OBJECT_LOCALIZATION
                );

        return response;
    }
}
