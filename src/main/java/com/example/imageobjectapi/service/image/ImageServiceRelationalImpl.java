package com.example.imageobjectapi.service.image;

import com.example.imageobjectapi.dto.request.ImageProcessRequest;
import com.example.imageobjectapi.entity.ImageEntity;
import com.example.imageobjectapi.entity.ObjectEntity;
import com.example.imageobjectapi.exception.NoImageToProcessException;
import com.example.imageobjectapi.repository.ImageRelationalRepo;
import com.example.imageobjectapi.repository.ObjectRepo;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class ImageServiceRelationalImpl implements ImageService {
    private final ImageRelationalRepo imageRepo;
    private final ObjectRepo objectRepo;
    private final VisionService visionService;

    public List<ImageEntity> getAllImages() {
        return this.imageRepo.findAll();
    }

    public List<ImageEntity> getAllImagesWithObjects(List<String> objects) {
        Set<ObjectEntity> preExistingObjects = this.objectRepo.getObjectEntitiesByNameIn(objects.stream().map(String::toLowerCase).collect(Collectors.toList()));
        return this.imageRepo.findByObjectsIn(preExistingObjects);
    }

    public Optional<ImageEntity> getImageById(String id) {
        return imageRepo.findById(id);
    }

    public ImageEntity processImage(ImageProcessRequest request, MultipartFile imageDocument) throws IOException {
        if(StringUtils.isEmpty(request.getUrl()) && (ObjectUtils.isEmpty(imageDocument) || imageDocument.getSize() <= 0)) {
            throw new NoImageToProcessException("No Image uploaded");
        }

        if(request.getProcessImageForObjects()) {
            AnnotateImageResponse response = (ObjectUtils.isEmpty(imageDocument) || imageDocument.getSize() <= 0) ?
                    visionService.analyzeImage(request.getUrl()) :
                    visionService.analyzeImage(imageDocument);

            List<String> objects = response.getLocalizedObjectAnnotationsList().stream()
                    .map(item -> item.getName().toLowerCase())
                    .distinct()
                    .collect(Collectors.toList());

            Set<ObjectEntity> preExistingObjects = objectRepo.getObjectEntitiesByNameIn(objects);
            preExistingObjects.addAll(objects.stream().map(x -> ObjectEntity.builder().name(x).build())
                    .collect(Collectors.toSet()));

            return imageRepo.save(ImageEntity.builder()
                    .label(StringUtils.isEmpty(request.getLabel()) ?
                            String.join("", "LABEL-", RandomStringUtils.random(12, true, true)) :
                            request.getLabel())
                    .metadata(response.getLocalizedObjectAnnotationsList().toString())
                    .objects(preExistingObjects)
                    .image((ObjectUtils.isEmpty(imageDocument) || imageDocument.getSize() <= 0)
                            ? new byte[0] : new String(imageDocument.getBytes(), StandardCharsets.UTF_8).getBytes())
                    .imageUrl(StringUtils.isEmpty(request.getUrl()) ? null : request.getUrl())
                    .build()
            );
        } else {
            return imageRepo.save(ImageEntity.builder()
                    .label(StringUtils.isEmpty(request.getLabel()) ?
                            String.join("", "LABEL -", RandomStringUtils.random(12, true, true)) :
                            request.getLabel())
                            .objects(new HashSet<>())
                    .image((ObjectUtils.isEmpty(imageDocument) || imageDocument.getSize() <= 0)
                            ? new byte[0] : new String(imageDocument.getBytes(), StandardCharsets.UTF_8).getBytes())
                    .imageUrl(StringUtils.isEmpty(request.getUrl()) ? null : request.getUrl())
                    .build());
        }
    }
}
