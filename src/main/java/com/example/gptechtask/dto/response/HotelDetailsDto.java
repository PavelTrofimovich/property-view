package com.example.gptechtask.dto.response;

import com.example.gptechtask.entity.Address;
import com.example.gptechtask.entity.ArrivalTime;
import com.example.gptechtask.entity.Contacts;
import java.util.List;

public record HotelDetailsDto(
    Long id,
    String name,
    String description,
    String brand,
    Address address,
    Contacts contacts,
    ArrivalTime arrivalTime,
    List<String> amenities
) {

}
