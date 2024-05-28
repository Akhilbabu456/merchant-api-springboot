package com.akhil.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.akhil.service.MerchantDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.akhil.model.Category;
import com.akhil.model.Merchant;
import com.akhil.repository.MerchantRepository;
import com.akhil.repository.CategoryRepository;

import jakarta.validation.Valid;



@Service
public class MerchantServiceImplementation implements MerchantService{

    @Autowired
    private MerchantRepository merchantRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Merchant findUserById(Long userId) throws Exception {
         Optional<Merchant> opt = merchantRepository.findById(userId);
         if(opt.isPresent()){
            return opt.get();  
          }
          throw new Exception("user doesnot exists");
    }

    @Override
    public List<Merchant>findAllUsers(){
        return merchantRepository.findAll();
    } 

    @Override
    public Merchant createUser(@Valid MerchantDTO userDto) throws Exception{
      String userName = userDto.getUserName();
        String shopName = userDto.getShopName();
        String phoneNumber = userDto.getPhoneNumber();
        String categoryName = userDto.getCategoryName();
      
        System.out.println("Category name: " + categoryName);

        // Check if the category exists by name
        Optional<Category> categoryOpt = categoryRepository.findByCategory(categoryName);

        // Debug statement to check the fetched category
        System.out.println("Fetched category: " + categoryOpt);
        if (!categoryOpt.isPresent()) {
            throw new Exception("Category does not exist");
        }

      try{
        Merchant createdMerchant = new Merchant();
        createdMerchant.setUserName(userName);
        createdMerchant.setShopName(shopName);
        createdMerchant.setPhoneNumber(phoneNumber);
        createdMerchant.setCategory(categoryOpt.get());
        createdMerchant.setCreatedAt(LocalDateTime.now());
      
        return merchantRepository.save(createdMerchant);
      }catch(Exception e){
        throw new Exception(e);
      }
    }

    @Override
    public void deleteUserById(Long id) throws Exception{
      findUserById(id);
        merchantRepository.deleteById(id);
    } 
    
} 
