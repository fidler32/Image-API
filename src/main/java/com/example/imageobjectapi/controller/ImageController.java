package com.example.imageobjectapi.controller;

import com.example.imageobjectapi.document.ImageDocument;
import com.example.imageobjectapi.dto.request.ImageProcessRequest;
import com.example.imageobjectapi.exception.ImageNotFoundException;
import com.example.imageobjectapi.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
public class ImageController {
    private final ImageService imageService;

    @GetMapping(path = {"/images"})
    public List<ImageDocument> getImages(@RequestParam(value ="object", required = false) List<String> objects){
        if(CollectionUtils.isEmpty(objects)){
            return this.imageService.getAllImages();
        }else {
            return this.imageService.getAllImagesWithObjects(objects);
        }
    }

    @GetMapping(path = {"/images/{id}"})
    public ImageDocument getImageById(@PathVariable String id){
        return this.imageService
                .getImageById(id).orElseThrow(() -> new ImageNotFoundException("Image requested was not found"));

    }

    @PostMapping(path= {"/images"})
    public ImageDocument processImage(
             @RequestPart ImageProcessRequest request, @RequestPart(required = false) MultipartFile processImage) throws IOException {

        log.info(request.toString());
        return this.imageService.processImage(request, processImage);
    }
}
