package com.example.gptechtask.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Hotel contact details")
public record ContactsDto(
    @Schema(example = "+375 17 309-80-00")
    @NotBlank
    String phone,

    @Schema(example = "doubletreeminsk.info@hilton.com")
    @Email
    String email
) {

}
