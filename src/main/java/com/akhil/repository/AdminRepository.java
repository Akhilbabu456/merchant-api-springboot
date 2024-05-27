package com.akhil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akhil.model.Admin;
public interface AdminRepository extends JpaRepository<Admin, Long>{
    public Admin findByEmail(String email);
}
