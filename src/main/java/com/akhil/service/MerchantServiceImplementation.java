package com.akhil.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.akhil.config.JwtProvider;
import com.akhil.model.Merchant;
import com.akhil.repository.MerchantRepository;


@Service
public class MerchantServiceImplementation implements MerchantService{
  
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MerchantRepository userRepository;
    
    @Autowired
    private CategoryService postService;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public Merchant findUserById(Long userId) throws Exception {
         Optional<Merchant> opt = userRepository.findById(userId);
         if(opt.isPresent()){
            return opt.get();  
          }
          throw new Exception("user doesnot exists");
    }

    @Override
    public Merchant findUserByJwt(String jwt) throws Exception {
      
      String email = jwtProvider.getEmailFromJwtToken(jwt);
      
      if(email==null){
        throw new Exception("Provide valid JWT token");
      }

      Merchant user = userRepository.findByEmail(email);
      
      if(user==null){
        throw new Exception("User doesnot exists");
      }
      return user;
    }

    @Override
    public List<Merchant>findAllUsers(){
        return userRepository.findAll();
    } 

    @Override
    public Merchant updateUserById(Merchant user,Long id) throws Exception{
        Merchant oldUser = findUserById(id);
        String userName = user.getUserName();
        String email = user.getEmail();
        String password = user.getPassword();
        String profilePicture = user.getProfilePicture();

        if(userName!=null){
            oldUser.setUserName(userName);
        }
        if(email!=null){
          oldUser.setEmail(email);
        }
        if(password!=null){
          oldUser.setPassword(passwordEncoder.encode(password));
        }
        if(profilePicture!=null){
          oldUser.setProfilePicture(profilePicture);
        }
        
        return userRepository.save(oldUser);
    }

    @Override
    public void deleteUserById(Long id) throws Exception{
      postService.deletePostByUser(id);
      findUserById(id);
        userRepository.deleteById(id);
    } 
    
} 
