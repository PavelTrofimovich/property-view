package com.example.gptechtask.controller;

import com.example.gptechtask.dto.request.CreateHotelRequest;
import com.example.gptechtask.dto.request.HotelSearchRequest;
import com.example.gptechtask.dto.response.HotelDetailsDto;
import com.example.gptechtask.dto.response.HotelShortDto;
import com.example.gptechtask.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/property-view")
@RequiredArgsConstructor
public class HotelController {

  private final HotelService hotelService;

  @Operation(summary = "Get all hotels")
  @GetMapping("/hotels")
  public List<HotelShortDto> getHotels() {
    return hotelService.getAllHotels();
  }

  @Operation(summary = "Get hotel by ID")
  @GetMapping("/hotels/{id}")
  public HotelDetailsDto getHotel(@PathVariable Long id) {
    return hotelService.getHotelById(id);
  }

  @Operation(summary = "Create a new hotel")
  @PostMapping("/hotels")
  public HotelShortDto createHotel(@Valid @RequestBody CreateHotelRequest request) {
    return hotelService.createHotel(request);
  }

  @Operation(summary = "Add amenities to hotel")
  @PostMapping("/hotels/{id}/amenities")
  public void addAmenities(@PathVariable Long id, @RequestBody List<String> amenities) {
    hotelService.addAmenities(id, amenities);
  }

  @Operation(summary = "Search hotels")
  @GetMapping("/search")
  public List<HotelShortDto> searchHotels(@ModelAttribute HotelSearchRequest request) {
    return hotelService.searchHotels(request);
  }

  @Operation(
      summary = "Get hotels histogram",
      description = "Supported parameters: brand, city, country, amenities"
  )
  @GetMapping("/histogram/{param}")
  public Map<String, Long> getHistogram(@PathVariable String param) {
    return hotelService.getHistogram(param);
  }
}