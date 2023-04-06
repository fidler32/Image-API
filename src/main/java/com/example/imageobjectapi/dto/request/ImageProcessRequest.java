package com.example.imageobjectapi.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@Jacksonized
public class ImageProcessRequest {
    private String url;
    private String label;
    private boolean processImageForObjects;
//    private MultipartFile imageDocument;
}
