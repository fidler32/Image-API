package com.example.imageobjectapi.service.image;

import com.example.imageobjectapi.document.ImageDocument;
import com.example.imageobjectapi.dto.request.ImageProcessRequest;
import com.example.imageobjectapi.exception.NoImageToProcessException;
import com.example.imageobjectapi.repository.ImageRepo;
import com.example.imageobjectapi.service.googleVision.VisionService;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepo imageRepo;
    private final VisionService visionService;
    @Override
    public List<ImageDocument> getAllImages() {
        return this.imageRepo.findAll();
    }
    @Override
    public List<ImageDocument> getAllImagesWithObjects(List<String> objects) {
        return this.imageRepo.findByObjectsContainsIgnoreCase(objects);
    }
    @Override
    public Optional<ImageDocument> getImageById(String id) {
        return imageRepo.findById(id);
    }

    public ImageDocument saveImage(ImageDocument imageDocument) {
        return this.imageRepo.save(imageDocument);
    }

    @Override
    public ImageDocument processImage(ImageProcessRequest request, MultipartFile imageDocument) throws IOException {
        if(StringUtils.isEmpty(request.getUrl()) && (ObjectUtils.isEmpty(imageDocument) || imageDocument.getSize() <= 0)) {
            throw new NoImageToProcessException("No Image uploaded");
        }

        if(request.getProcessImageForObjects()) {
            AnnotateImageResponse response = (ObjectUtils.isEmpty(imageDocument) || imageDocument.getSize() <= 0) ?
                    visionService.analyzeImage(request.getUrl()) :
                    visionService.analyzeImage(imageDocument);

            List<String> objects = response.getLocalizedObjectAnnotationsList().stream()
                    .map(item -> item.getName())
                    .distinct()
                    .collect(Collectors.toList());

            return imageRepo.save(ImageDocument.builder()
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
            return imageRepo.save(ImageDocument.builder()
                    .label(StringUtils.isEmpty(request.getLabel()) ?
                            String.join("", "LABEL -", RandomStringUtils.random(12, true, true)) :
                            request.getLabel())
                    .image((ObjectUtils.isEmpty(imageDocument) || imageDocument.getSize() <= 0) ?
                            request.getUrl() : new String(imageDocument.getBytes(), StandardCharsets.UTF_8))
                    .build());
        }
    }
}
