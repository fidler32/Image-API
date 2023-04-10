package com.example.imageobjectapi.controller;

import com.example.imageobjectapi.document.ImageDocument;
import com.example.imageobjectapi.dto.request.ImageProcessRequest;
import com.example.imageobjectapi.service.googleVision.VisionService;
import com.example.imageobjectapi.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@Slf4j
public class ImageController {
    private final ImageService imageService;
    private final VisionService visionService;

    @GetMapping(path = {"/images"})
    public List<ImageDocument> getImages(@RequestParam(value ="object", required = false) List<String> objects){
        if(objects.isEmpty()){
            return this.imageService.getAllImages();
        }else {
            return this.imageService.getAllImagesWithObjects(objects);
        }
    }

    @GetMapping(path = {"/images/{id}"})
    public ImageDocument getImageById(@PathVariable String id){
        return this.imageService
                .getImageById(id).orElseThrow(() -> new RuntimeException("asdsad"));

    }

    @PostMapping(path= {"/images"})
    public ImageDocument processImage(
             @RequestPart ImageProcessRequest request, @RequestPart(required = false) MultipartFile processImage) throws IOException {

        log.info(request.toString());
        return imageService.processImage(request, processImage);
    }
}
