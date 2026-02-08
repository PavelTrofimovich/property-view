package com.example.gptechtask.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Hotel search filters")
public record HotelSearchRequest(
    @Schema(example = "DoubleTree by Hilton Minsk", description = "Hotel name contains")
    String name,

    @Schema(example = "Hilton")
    String brand,

    @Schema(example = "Minsk")
    String city,

    @Schema(example = "Belarus")
    String country,

    @Schema(
        example = "[\"Free parking\", \"Free WiFi\"]",
        description = "Required amenities"
    )
    List<String> amenities
) {

}
