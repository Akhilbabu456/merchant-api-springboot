package com.akhil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akhil.model.Merchant;

public interface MerchantRepository extends JpaRepository<Merchant, Long>{
    
    
}
