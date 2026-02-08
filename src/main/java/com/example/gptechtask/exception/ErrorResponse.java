package com.example.gptechtask.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Error response")
public record ErrorResponse(
    String message
) {
}
