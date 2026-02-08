package com.example.gptechtask.service;

import com.example.gptechtask.dto.request.CreateHotelRequest;
import com.example.gptechtask.dto.request.HotelSearchRequest;
import com.example.gptechtask.dto.response.HotelDetailsDto;
import com.example.gptechtask.dto.response.HotelShortDto;
import java.util.List;
import java.util.Map;

public interface HotelService {

  List<HotelShortDto> getAllHotels();

  HotelDetailsDto getHotelById(Long id);

  HotelShortDto createHotel(CreateHotelRequest request);

  void addAmenities(Long hotelId, List<String> amenityNames);

  List<HotelShortDto> searchHotels(HotelSearchRequest request);

  Map<String, Long> getHistogram(String param);
}
