package com.akhil.service;

import java.util.List;

import com.akhil.model.Category;
import com.akhil.model.Merchant;


public interface CategoryService {
    
    public Category createPost(Category post, Merchant user) throws Exception;
    public Category findPostById(Long id) throws Exception;
    public List<Category> getPostByUserId(Long id) throws Exception;
    public void deletePost(Long id) throws Exception;
    public void deletePostByUser(Long id) throws Exception;
    public Category updatePost(Category post,Long id) throws Exception;
    public List<Category>findAllPost();
    public List<Category>findPostsByTitle(String title);
    public List<Category>findPostsByContent(String content);
    public List<Category>findPostsByCategory(String category);
    public List<Category>findPostsByTitleAndCategory(String title, String category);
    
}

