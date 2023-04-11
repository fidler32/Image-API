//package com.example.imageobjectapi.service.image;
//
//import com.example.imageobjectapi.document.ImageDocument;
//import com.example.imageobjectapi.dto.request.ImageProcessRequest;
//import com.example.imageobjectapi.exception.NoImageToProcessException;
//import com.example.imageobjectapi.service.googleVision.VisionService;
//import com.google.cloud.vision.v1.AnnotateImageResponse;
//import com.google.cloud.vision.v1.LocalizedObjectAnnotation;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.io.IOException;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static java.util.Arrays.asList;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.fail;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class ImageServiceImplTest {
//    @Mock
//    private ImageRepo imageRepo;
//
//    @Mock
//    private VisionService visionService;
//
//    @InjectMocks
//    private ImageServiceImpl subject;
//
//
//    @Test
//    void getAllImages() {
//        List<ImageDocument> images = Collections.emptyList();
//        when(imageRepo.findAll()).thenReturn(images);
//        assertThat(subject.getAllImages()).isEqualTo(images);
//    }
//
//    @Test
//    void getAllImagesWithObjects() {
//        List<String> objects = asList("stuff");
//        List<ImageDocument> images = Collections.emptyList();
//
//        when(imageRepo.findByObjectsContainsIgnoreCase(anyList())).thenReturn(images);
//
//        assertThat(subject.getAllImagesWithObjects(objects)).isEqualTo(images);
//    }
//
//    @Test
//    void getImageById() {
//        ImageDocument image = ImageDocument.builder().build();
//
//        when(imageRepo.findById(anyString())).thenReturn(Optional.of(image));
//
//        assertThat(subject.getImageById("id").get()).isEqualTo(image);
//        verify(imageRepo).findById("id");
//    }
//
//    @Test
//    void processImageFailIfNoUrlOrImageDocument() throws IOException {
//        try {
//            subject.processImage(ImageProcessRequest.builder().build(), null);
//            fail("Expected exception not thrown");
//        }catch(NoImageToProcessException e){
//            assertThat(e.getMessage()).isEqualTo("No Image uploaded");
//        }
//    }
//
//    @Test
//    void processImage() throws IOException {
//        when(visionService.analyzeImage(anyString())).thenReturn(AnnotateImageResponse.newBuilder()
//                        .addAllLocalizedObjectAnnotations(asList(LocalizedObjectAnnotation.newBuilder().build()))
//                .build());
//        ImageDocument doc = ImageDocument.builder().build();
//
//        when(imageRepo.save(any(ImageDocument.class))).thenReturn(doc);
//        ImageProcessRequest request = ImageProcessRequest.builder()
//                .processImageForObjects(true)
//                .label("label")
//                .url("url")
//                .build();
//
//        assertThat(subject.processImage(request, null)).isEqualTo(doc);
//    }
//}