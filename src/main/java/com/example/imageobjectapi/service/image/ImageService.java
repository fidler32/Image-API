package com.example.imageobjectapi.service.image;

import com.example.imageobjectapi.document.ImageDocument;
import com.example.imageobjectapi.dto.request.ImageProcessRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ImageService {
    List<ImageDocument> getAllImages();
    List<ImageDocument> getAllImagesWithObjects(List<String> objects);

    Optional<ImageDocument> getImageById(String id);

    ImageDocument processImage(ImageProcessRequest request, MultipartFile imageDocument) throws IOException;
}
