package com.example.gptechtask.repository.specification;

import com.example.gptechtask.dto.request.HotelSearchRequest;
import com.example.gptechtask.entity.Hotel;
import com.example.gptechtask.entity.Amenity;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class HotelSpecification {

  public static Specification<Hotel> withFilter(HotelSearchRequest request) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (StringUtils.hasText(request.name())) {
        predicates.add(cb.like(cb.lower(root.get("name")), "%" + request.name().toLowerCase() + "%"));
      }

      if (StringUtils.hasText(request.brand())) {
        predicates.add(cb.like(cb.lower(root.get("brand")), "%" + request.brand().toLowerCase() + "%"));
      }

      if (StringUtils.hasText(request.city())) {
        predicates.add(cb.like(cb.lower(root.get("address").get("city")), "%" + request.city().toLowerCase() + "%"));
      }

      if (StringUtils.hasText(request.country())) {
        predicates.add(cb.like(cb.lower(root.get("address").get("country")), "%" + request.country().toLowerCase() + "%"));
      }

      if (!CollectionUtils.isEmpty(request.amenities())) {
        Join<Hotel, Amenity> amenitiesJoin = root.join("amenities");
        predicates.add(amenitiesJoin.get("name").in(request.amenities()));

        query.distinct(true);
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
