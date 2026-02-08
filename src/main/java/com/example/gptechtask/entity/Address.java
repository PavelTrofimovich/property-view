package com.example.gptechtask.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Address {
  private String houseNumber;
  private String street;
  private String city;
  private String country;
  private String postCode;

  public String toShortString() {
    return houseNumber + " " + street + ", " +
           city + ", " + postCode + ", " + country;
  }
}
