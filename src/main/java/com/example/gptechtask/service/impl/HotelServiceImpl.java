package com.example.gptechtask.service.impl;

import com.example.gptechtask.dto.request.CreateHotelRequest;
import com.example.gptechtask.dto.request.HotelSearchRequest;
import com.example.gptechtask.dto.response.HotelDetailsDto;
import com.example.gptechtask.dto.response.HotelShortDto;
import com.example.gptechtask.entity.Amenity;
import com.example.gptechtask.entity.Hotel;
import com.example.gptechtask.exception.exceptions.NotFoundException;
import com.example.gptechtask.exception.exceptions.UnsupportedHistogramParameterException;
import com.example.gptechtask.mapper.HotelMapper;
import com.example.gptechtask.repository.AmenityRepository;
import com.example.gptechtask.repository.HotelRepository;
import com.example.gptechtask.repository.specification.HotelSpecification;
import com.example.gptechtask.service.HotelService;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

  private final HotelRepository hotelRepository;
  private final AmenityRepository amenityRepository;
  private final HotelMapper hotelMapper;

  @Override
  public List<HotelShortDto> getAllHotels() {
    return hotelMapper.toShortDtoList(hotelRepository.findAll());
  }

  @Override
  public HotelDetailsDto getHotelById(Long id) {
    Hotel hotel = hotelRepository.findByIdWithAmenities(id)
        .orElseThrow(() -> new NotFoundException("Hotel not found: " + id));

    return hotelMapper.toDetailsDto(hotel);
  }

  @Override
  @Transactional
  public HotelShortDto createHotel(CreateHotelRequest request) {
    Hotel hotel = hotelMapper.toEntity(request);
    hotel = hotelRepository.save(hotel);

    return hotelMapper.toShortDto(hotel);
  }

  @Override
  @Transactional
  public void addAmenities(Long hotelId, List<String> amenityNames) {
    Hotel hotel = hotelRepository.findByIdWithAmenities(hotelId)
        .orElseThrow(() -> new NotFoundException("Hotel not found with id: " + hotelId));

    if (amenityNames == null || amenityNames.isEmpty()) {
      return;
    }

    Set<String> distinctNames = new HashSet<>(amenityNames);

    List<Amenity> existingAmenities = amenityRepository.findByNameIn(distinctNames);
    Map<String, Amenity> existingMap = existingAmenities.stream()
        .collect(Collectors.toMap(Amenity::getName, Function.identity()));

    for (String name : distinctNames) {
      if (existingMap.containsKey(name)) {
        hotel.addAmenity(existingMap.get(name));
      } else {
        Amenity newAmenity = new Amenity();
        newAmenity.setName(name);
        hotel.addAmenity(newAmenity);
      }
    }

    hotelRepository.save(hotel);
  }

  @Override
  public List<HotelShortDto> searchHotels(HotelSearchRequest request) {
    Specification<Hotel> spec = HotelSpecification.withFilter(request);

    List<Hotel> foundHotels = hotelRepository.findAll(spec);

    return hotelMapper.toShortDtoList(foundHotels);
  }

  @Override
  public Map<String, Long> getHistogram(String param) {
    List<Object[]> result = switch (param.toLowerCase()) {
      case "brand" -> hotelRepository.countByBrand();
      case "city" -> hotelRepository.countByCity();
      case "country" -> hotelRepository.countByCountry();
      case "amenities" -> hotelRepository.countByAmenities();
      default -> throw new UnsupportedHistogramParameterException(param);
    };

    return result.stream()
        .collect(Collectors.toMap(
            row -> (String) row[0],
            row -> (Long) row[1]
        ));
  }
}
