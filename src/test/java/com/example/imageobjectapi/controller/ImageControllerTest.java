//package com.example.imageobjectapi.controller;
//
//import com.example.imageobjectapi.document.ImageDocument;
//import com.example.imageobjectapi.dto.request.ImageProcessRequest;
//import com.example.imageobjectapi.service.image.ImageService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.awt.*;
//import java.io.IOException;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ImageControllerTest {
//    @Mock
//    private ImageService imageService;
//
//    @InjectMocks
//    private ImageController subject;
//
//    @Test
//    void getImages() {
//        List<ImageDocument> expected = List.of(ImageDocument.builder().build());
//        when(imageService.getAllImages()).thenReturn(expected);
//
//        List<ImageDocument> documents = subject.getImages(Collections.emptyList());
//
//        assertEquals(expected, documents);
//    }
//
//    @Test
//    void getImagesWithParams() {
//        List<ImageDocument> expected = List.of(ImageDocument.builder().build());
//        List<String> params = List.of("stuff,three");
//        when(imageService.getAllImagesWithObjects(anyList())).thenReturn(expected);
//
//        List<ImageDocument> documents = subject.getImages(params);
//
//        assertEquals(expected, documents);
//        verify(imageService, times(1)).getAllImagesWithObjects(params);
//    }
//
//    @Test
//    void getImageById() {
//        ImageDocument expected = ImageDocument.builder().build();
//        when(imageService.getImageById(anyString())).thenReturn(Optional.of(expected));
//
//        ImageDocument imageDocument = subject.getImageById("string");
//
//        assertEquals(expected, imageDocument);
//        verify(imageService, times(1)).getImageById("string");
//    }
//
//
//    @Test
//    void processImage() throws IOException {
//        ImageDocument expected = ImageDocument.builder().build();
//        when(imageService.processImage(any(ImageProcessRequest.class), eq(null))).thenReturn(expected);
//        ImageProcessRequest request = ImageProcessRequest.builder().build();
//
//        ImageDocument imageDocument = subject.processImage(request, null);
//
//        assertEquals(expected, imageDocument);
//        verify(imageService, times(1)).processImage(eq(request), isNull());
//    }
//}