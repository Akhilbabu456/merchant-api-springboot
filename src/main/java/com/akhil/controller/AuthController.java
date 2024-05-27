package com.akhil.controller;

import com.akhil.config.JwtProvider;
import com.akhil.model.Admin;
import com.akhil.repository.AdminRepository;
import com.akhil.request.LoginRequest;
import com.akhil.response.AuthResponse;
import com.akhil.service.CustomUserDetailsService;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AdminRepository adminRepository;

  @Autowired
  private CustomUserDetailsService customUserDetails;

  @Autowired
  private JwtProvider jwtProvider;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @PostMapping("/signup")
  public AuthResponse createUser(@RequestBody Admin admin) throws Exception {
    String email = admin.getEmail();
    String password = admin.getPassword();
    try{
      if(email == null || email.isEmpty() || 
      password == null || password.isEmpty()){
          throw new Exception("All fields are required");
      }
  
      Admin isExistEmail = adminRepository.findByEmail(email);
      if (isExistEmail != null) {
        throw new Exception("Email already exist");
      }
      Admin createdUser = new Admin();
      createdUser.setEmail(email);
      createdUser.setPassword(passwordEncoder.encode(password));
  
     Admin savedUser = adminRepository.save(createdUser);
  
      Authentication authentication = new UsernamePasswordAuthenticationToken(
        email,
        password
      );
  
      SecurityContextHolder.getContext().setAuthentication(authentication);
  
      String token = jwtProvider.generateToken(authentication);
  
      AuthResponse res = new AuthResponse();
  
      res.setJwt(token);
      res.setMessage("Signup successful");
  
      return res;
    }catch(Exception e){
      AuthResponse res = new AuthResponse();
      res.setMessage( e.getMessage());
      return res;
    }
  }
  
  @PostMapping("/signin")
  public AuthResponse signinHandler(@RequestBody LoginRequest loginRequest) {
    String username = loginRequest.getEmail();
    String password = loginRequest.getPassword();
  try{
    Authentication authentication = authenticate(username, password);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String token = jwtProvider.generateToken(authentication);
    Admin admin = adminRepository.findByEmail(username);
    AuthResponse res = new AuthResponse();

    res.setJwt(token);
    res.setAdmin(admin);

    return res;
  }catch(Exception e){
    AuthResponse res = new AuthResponse();
    res.setMessage( e.getMessage());
    return res;
  }
  }

  private Authentication authenticate(String username, String password) throws Exception{
    UserDetails userDetails = customUserDetails.loadUserByUsername(username);

    if (userDetails == null) {
      throw new Exception("User not found");
    }

    if (!passwordEncoder.matches(password, userDetails.getPassword())) {
      throw new Exception("Wrong password");
    }

    return new UsernamePasswordAuthenticationToken(
      userDetails,
      null,
      userDetails.getAuthorities()
    );
  }
}
