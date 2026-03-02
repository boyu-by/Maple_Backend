package com.example.maple.model.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ApiErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
