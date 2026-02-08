package com.example.gptechtask.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request for creating a hotel")
public record CreateHotelRequest(
    @Schema(example = "DoubleTree by Hilton Minsk", description = "Hotel name")
    @NotBlank
    String name,

    @Schema(example = "The DoubleTree by Hilton Hotel Minsk offers ...", description = "Optional description")
    String description,

    @Schema(example = "Hilton", description = "Hotel brand")
    @NotBlank
    String brand,

    @Schema(description = "Hotel address")
    @NotNull
    @Valid
    AddressDto address,

    @Schema(description = "Contact information")
    @NotNull
    @Valid
    ContactsDto contacts,

    @Schema(description = "Check-in and check-out time")
    @NotNull
    @Valid
    ArrivalTimeDto arrivalTime
) {

}
