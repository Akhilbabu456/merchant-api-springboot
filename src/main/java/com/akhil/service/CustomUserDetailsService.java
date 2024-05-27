package com.akhil.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.akhil.model.Admin;
import com.akhil.repository.AdminRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private AdminRepository adminRepository;
    @Override
     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Admin admin = adminRepository.findByEmail(username);

        if(admin==null){
            throw new UsernameNotFoundException("User not found with email");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();

        return new org.springframework.security.core.userdetails.User(admin.getEmail(),admin.getPassword(),authorities);
     }
}
