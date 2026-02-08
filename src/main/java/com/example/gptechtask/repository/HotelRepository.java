package com.example.gptechtask.repository;

import com.example.gptechtask.entity.Hotel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HotelRepository extends JpaRepository<Hotel, Long>,
    JpaSpecificationExecutor<Hotel> {

  @Query("""
        SELECT h
        FROM Hotel h
        LEFT JOIN FETCH h.amenities
        WHERE h.id = :id
    """)
  Optional<Hotel> findByIdWithAmenities(@Param("id") Long id);

  @Query("SELECT h.brand, COUNT(h) FROM Hotel h GROUP BY h.brand")
  List<Object[]> countByBrand();

  @Query("SELECT h.address.city, COUNT(h) FROM Hotel h GROUP BY h.address.city")
  List<Object[]> countByCity();

  @Query("SELECT h.address.country, COUNT(h) FROM Hotel h GROUP BY h.address.country")
  List<Object[]> countByCountry();

  @Query("SELECT a.name, COUNT(DISTINCT h.id) FROM Hotel h JOIN h.amenities a GROUP BY a.name")
  List<Object[]> countByAmenities();
}
