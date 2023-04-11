package com.example.imageobjectapi.service.image;

import com.example.imageobjectapi.dto.request.ImageProcessRequest;
import com.example.imageobjectapi.entity.ImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ImageService {
    List<ImageEntity> getAllImages();
    List<ImageEntity> getAllImagesWithObjects(List<String> objects);
    Optional<ImageEntity> getImageById(String id);

    ImageEntity processImage(ImageProcessRequest request, MultipartFile imageDocument) throws IOException;
}
