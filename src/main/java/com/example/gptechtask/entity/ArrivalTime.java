package com.example.gptechtask.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class ArrivalTime {
  @Column(nullable = false)
  @JsonFormat(pattern = "HH:mm")
  private LocalTime checkIn;

  @JsonFormat(pattern = "HH:mm")
  private LocalTime checkOut;
}
