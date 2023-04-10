package com.example.imageobjectapi.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ImageProcessRequest {
    private String url;
    private String label;
    @JsonProperty(defaultValue = "false")
    private Boolean processImageForObjects;

}
