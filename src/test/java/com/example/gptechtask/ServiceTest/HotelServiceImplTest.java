package com.example.gptechtask.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.example.gptechtask.service.impl.HotelServiceImpl;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
public class HotelServiceImplTest {

  @Mock
  private HotelRepository hotelRepository;

  @Mock
  private AmenityRepository amenityRepository;

  @Mock
  private HotelMapper hotelMapper;

  @InjectMocks
  private HotelServiceImpl hotelService;

  @Test
  @DisplayName("Should return list of short hotel DTOs")
  void getAllHotelsShouldReturnShortDtos() {
    Hotel hotel = new Hotel();
    HotelShortDto dto = new HotelShortDto(1L, "Hotel", null, "Address", "123");

    when(hotelRepository.findAll()).thenReturn(List.of(hotel));
    when(hotelMapper.toShortDtoList(List.of(hotel))).thenReturn(List.of(dto));

    List<HotelShortDto> result = hotelService.getAllHotels();

    assertEquals(1, result.size());
    assertEquals("Hotel", result.get(0).name());
  }

  @Test
  @DisplayName("Should return hotel details by id")
  void getHotelByIdShouldReturnDetails() {
    Hotel hotel = new Hotel();
    HotelDetailsDto dto = mock(HotelDetailsDto.class);

    when(hotelRepository.findByIdWithAmenities(1L))
        .thenReturn(Optional.of(hotel));
    when(hotelMapper.toDetailsDto(hotel)).thenReturn(dto);

    HotelDetailsDto result = hotelService.getHotelById(1L);

    assertNotNull(result);
  }

  @Test
  @DisplayName("Should throw NotFoundException when hotel does not exist")
  void getHotelByIdShouldThrowNotFoundException() {
    when(hotelRepository.findByIdWithAmenities(1L))
        .thenReturn(Optional.empty());

    assertThrows(
        NotFoundException.class,
        () -> hotelService.getHotelById(1L)
    );
  }

  @Test
  @DisplayName("Should create hotel and return short DTO")
  void createHotelShouldSaveHotelAndReturnShortDto() {
    CreateHotelRequest request = mock(CreateHotelRequest.class);
    Hotel hotel = new Hotel();
    Hotel savedHotel = new Hotel();
    HotelShortDto dto = mock(HotelShortDto.class);

    when(hotelMapper.toEntity(request)).thenReturn(hotel);
    when(hotelRepository.save(hotel)).thenReturn(savedHotel);
    when(hotelMapper.toShortDto(savedHotel)).thenReturn(dto);

    HotelShortDto result = hotelService.createHotel(request);

    assertNotNull(result);
    verify(hotelRepository).save(hotel);
  }

  @Test
  @DisplayName("Should add existing and new amenities to hotel")
  void addAmenitiesShouldAddExistingAndNewAmenities() {
    Hotel hotel = new Hotel();
    Amenity existingAmenity = new Amenity();
    existingAmenity.setName("WiFi");

    when(hotelRepository.findByIdWithAmenities(1L))
        .thenReturn(Optional.of(hotel));
    when(amenityRepository.findByNameIn(Set.of("Free WiFi", "Free parking")))
        .thenReturn(List.of(existingAmenity));

    hotelService.addAmenities(1L, List.of("Free WiFi", "Free parking"));

    assertEquals(2, hotel.getAmenities().size());
    verify(hotelRepository).save(hotel);
  }

  @Test
  @DisplayName("Should not save hotel when amenities list is empty")
  void addAmenitiesShouldDoNothingWhenListIsEmpty() {
    Hotel hotel = new Hotel();

    when(hotelRepository.findByIdWithAmenities(1L))
        .thenReturn(Optional.of(hotel));

    hotelService.addAmenities(1L, List.of());

    verify(hotelRepository, never()).save(any());
  }

  @Test
  @DisplayName("Should return hotels matching search filter")
  void searchHotelsShouldReturnFilteredHotels() {
    HotelSearchRequest request = new HotelSearchRequest(
        "Hilton", null, null, null, null
    );

    Hotel hotel = new Hotel();
    HotelShortDto dto = mock(HotelShortDto.class);

    when(hotelRepository.findAll(any(Specification.class)))
        .thenReturn(List.of(hotel));
    when(hotelMapper.toShortDtoList(List.of(hotel)))
        .thenReturn(List.of(dto));

    List<HotelShortDto> result = hotelService.searchHotels(request);

    assertEquals(1, result.size());
  }

  @Test
  @DisplayName("Should return histogram grouped by brand")
  void getHistogramShouldReturnBrandHistogram() {
    when(hotelRepository.countByBrand())
        .thenReturn(List.<Object[]>of(new Object[]{"Hilton", 2L}));

    Map<String, Long> result = hotelService.getHistogram("brand");

    assertEquals(1, result.size());
    assertEquals(2L, result.get("Hilton"));
  }

  @Test
  @DisplayName("Should throw exception for unsupported histogram parameter")
  void getHistogramShouldThrowExceptionForUnsupportedParam() {
    assertThrows(
        UnsupportedHistogramParameterException.class,
        () -> hotelService.getHistogram("price")
    );
  }
}
