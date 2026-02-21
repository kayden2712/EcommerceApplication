package org.example.ecommerceapplication.common.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private String error;
    private String path;
}