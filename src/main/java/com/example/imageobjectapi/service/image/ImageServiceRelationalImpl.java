package com.example.imageobjectapi.service.image;

import com.example.imageobjectapi.dto.request.ImageProcessRequest;
import com.example.imageobjectapi.entity.ImageEntity;
import com.example.imageobjectapi.exception.NoImageToProcessException;
import com.example.imageobjectapi.repository.ImageRelationalRepo;
import com.example.imageobjectapi.service.googleVision.VisionService;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ImageServiceRelationalImpl implements ImageService {
    private final ImageRelationalRepo imageRepo;
    private final VisionService visionService;

    public List<ImageEntity> getAllImages() {
        return this.imageRepo.findAll();
    }

    public List<ImageEntity> getAllImagesWithObjects(List<String> objects) {
        return this.imageRepo.findAllByObjectsIn(objects);
    }

    public Optional<ImageEntity> getImageById(String id) {
        return imageRepo.findById(id);
    }

    public ImageEntity processImage(ImageProcessRequest request, MultipartFile imageDocument) throws IOException {
        if(StringUtils.isEmpty(request.getUrl()) && (ObjectUtils.isEmpty(imageDocument) || imageDocument.getSize() < 0)) {
            throw new NoImageToProcessException("No Image uploaded");
        }

        if(request.getProcessImageForObjects()) {
            AnnotateImageResponse response = (ObjectUtils.isEmpty(imageDocument) || imageDocument.getSize() < 0) ?
                    visionService.analyzeImage(request.getUrl()) :
                    visionService.analyzeImage(imageDocument);

            List<String> objects = response.getLocalizedObjectAnnotationsList().stream()
                    .map(item -> item.getName())
                    .distinct()
                    .collect(Collectors.toList());

            return imageRepo.save(ImageEntity.builder()
                    .label(StringUtils.isEmpty(request.getLabel()) ?
                            String.join("", "LABEL-", RandomStringUtils.random(12, true, true)) :
                            request.getLabel())
                    .metadata(response.getLocalizedObjectAnnotationsList().toString())
                    .objects(objects)
                    .image(ObjectUtils.isEmpty(imageDocument)
                            ? request.getUrl() : new String(imageDocument.getBytes(), StandardCharsets.UTF_8))
                    .build()
            );
        } else {
            return imageRepo.save(ImageEntity.builder()
                    .label(StringUtils.isEmpty(request.getLabel()) ?
                            String.join("", "LABEL -", RandomStringUtils.random(12, true, true)) :
                            request.getLabel())
                    .image(ObjectUtils.isEmpty(imageDocument)
                            ? request.getUrl() : new String(imageDocument.getBytes(), StandardCharsets.UTF_8))
                    .build());
        }
    }
}
