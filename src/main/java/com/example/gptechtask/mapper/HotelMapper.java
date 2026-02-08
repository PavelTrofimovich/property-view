package com.example.gptechtask.mapper;

import com.example.gptechtask.dto.request.CreateHotelRequest;
import com.example.gptechtask.dto.response.HotelDetailsDto;
import com.example.gptechtask.dto.response.HotelShortDto;
import com.example.gptechtask.entity.Address;
import com.example.gptechtask.entity.Amenity;
import com.example.gptechtask.entity.Hotel;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface HotelMapper {

  @Mapping(target = "address", source = "address", qualifiedByName = "addressToString")
  @Mapping(target = "phone", source = "contacts.phone")
  HotelShortDto toShortDto(Hotel hotel);

  List<HotelShortDto> toShortDtoList(List<Hotel> hotels);

  @Mapping(target = "amenities", source = "amenities", qualifiedByName = "mapAmenitiesToStrings")
  HotelDetailsDto toDetailsDto(Hotel hotel);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "amenities", ignore = true)
  Hotel toEntity(CreateHotelRequest request);

  @Named("addressToString")
  default String addressToString(Address address) {
    return address != null ? address.toShortString() : null;
  }

  @Named("mapAmenitiesToStrings")
  default List<String> mapAmenitiesToStrings(Set<Amenity> amenities) {
    if (amenities == null) return Collections.emptyList();
    return amenities.stream()
        .map(Amenity::getName)
        .sorted()
        .toList();
  }
}
