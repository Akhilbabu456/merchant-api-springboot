package com.akhil.controller;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.akhil.response.CategoryResponse;
import com.akhil.model.Category;
import com.akhil.service.CategoryService;


@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/api/category")
    public CategoryResponse createPost(@RequestBody Category category, @RequestHeader("Authorization") String jwt) throws Exception{
        try{
            
            Category createdCategory = categoryService.createCategory(category);
            CategoryResponse res = new CategoryResponse();
            res.setCategory(createdCategory);
            res.setMessage("Category created successfully");
            return res;
        }catch(Exception e){
            CategoryResponse res = new CategoryResponse();
            res.setMessage(e.getMessage());
            return res;
        }
        
    }
    
   
}
