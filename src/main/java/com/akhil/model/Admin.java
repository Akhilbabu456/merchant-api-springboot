package com.akhil.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Admin {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String email;
  private String password;
  private LocalDateTime createdAt;


  @PrePersist
  protected void onCreate() {
      createdAt = LocalDateTime.now();
  }
}
