package com.example.imageobjectapi.service.googleVision;

import com.google.cloud.vision.v1.AnnotateImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface VisionService {
    AnnotateImageResponse analyzeImage(MultipartFile image);
    AnnotateImageResponse analyzeImage(String imageUrl);
}
