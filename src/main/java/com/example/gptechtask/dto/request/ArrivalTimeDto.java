package com.example.gptechtask.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

@Schema(description = "Arrival and departure time")
public record ArrivalTimeDto(
    @Schema(example = "14:00")
    @NotNull
    LocalTime checkIn,

    @Schema(example = "12:00")
    LocalTime checkOut
) {

}
