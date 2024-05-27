package com.akhil.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Merchant {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String shopName;
  private String phoneNumber;
  private String userName;
  private LocalDateTime createdAt;

   @ManyToOne
    private Category category;

  @PrePersist
  protected void onCreate() {
      createdAt = LocalDateTime.now();
  }
}
