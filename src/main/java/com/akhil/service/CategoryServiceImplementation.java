package com.akhil.service;

import java.time.LocalDateTime;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akhil.model.Category;
import com.akhil.repository.CategoryRepository;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Service
public class CategoryServiceImplementation implements CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    
    @Override
    public Category createCategory(@Valid Category category) throws Exception{
       
        @NotBlank
        String categor = category.getCategory()
        .replaceAll("\\s+", "-")
        .toLowerCase()
        .replaceAll("[^a-zA-Z0-9-]", "");
        try{
            Category createdCategory = new Category();
            createdCategory.setCategory(categor);
            createdCategory.setCreatedAt(LocalDateTime.now());
            
            return categoryRepository.save(createdCategory);
        }catch(Exception e){
             throw new Exception(e);
        }
           
        
    }


}
