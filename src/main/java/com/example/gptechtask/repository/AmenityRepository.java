package com.example.gptechtask.repository;

import com.example.gptechtask.entity.Amenity;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {

  List<Amenity> findByNameIn(Collection<String> names);
}
