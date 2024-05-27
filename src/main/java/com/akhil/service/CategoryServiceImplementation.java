package com.akhil.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akhil.model.Category;
import com.akhil.model.Merchant;
import com.akhil.repository.CategoryRepository;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Service
public class CategoryServiceImplementation implements CategoryService {
    
    @Autowired
    private CategoryRepository postRepository;
    
    
    @Override
    public Category createPost(@Valid Category post, Merchant user) throws Exception{
        @NotBlank
        String title = post.getTitle();
        @NotBlank
        String content = post.getContent();
        @NotBlank
        String image = post.getImage();
        @NotBlank
        String category = post.getCategory();
        String slug = post.getTitle().trim()
        .replaceAll("\\s+", "-")
        .toLowerCase()
        .replaceAll("[^a-zA-Z0-9-]", "");
        try{
            Category createdPost = new Category();
            createdPost.setTitle(title);
            createdPost.setContent(content);
            createdPost.setImage(image);
            createdPost.setUser(user);
            createdPost.setSlug(slug);
            createdPost.setCategory(category);
            createdPost.setCreatedAt(LocalDateTime.now());
            
            return postRepository.save(createdPost);
        }catch(Exception e){
             throw new Exception(e);
        }
           
        
    }

    @Override
    public Category findPostById(Long id) throws Exception{
        Optional<Category> opt = postRepository.findById(id);
        if(opt.isPresent()){
          return opt.get();  
        }
        throw new Exception("Post doesn't exists");
    }

    @Override
    public void deletePost(Long id) throws Exception{
        List<Category> posts = postRepository.findByUserId(id);
        postRepository.deleteAll(posts);
        
    }

    @Override
    public void deletePostByUser(Long id) throws Exception{
        findPostById(id);

        postRepository.deleteById(id);
        
    }


    @Override
    public Category updatePost(Category post,Long id) throws Exception{
        Category oldPost = findPostById(id);
        

        if(post.getTitle() != null){
            String slug = post.getTitle().trim()
            .replaceAll("\\s+", "-")
            .toLowerCase()
            .replaceAll("[^a-zA-Z0-9-]", "");

            oldPost.setTitle(post.getTitle());
            oldPost.setSlug(slug);
        }
        if(post.getImage() != null){
            oldPost.setImage(post.getImage());
        }
        if(post.getContent() != null){
            oldPost.setContent(post.getContent());
        }
        if(post.getCategory() != null){
            oldPost.setCategory(post.getCategory());
        }
        

        return postRepository.save(oldPost);
    }
    @Override
    public List<Category>findAllPost(){
        return postRepository.findAll();
    }

    @Override
    public List<Category> getPostByUserId(Long id){
        
        return postRepository.findByUserId(id);
    } 

    @Override
public List<Category> findPostsByTitle(String title) {
    return postRepository.findByTitleContainingIgnoreCase(title);
}

@Override
public List<Category> findPostsByContent(String content) {
    return postRepository.findByContentContainingIgnoreCase(content);
}

@Override
public List<Category> findPostsByCategory(String category) {
    return postRepository.findByCategoryIgnoreCase(category);
}

@Override
public List<Category> findPostsByTitleAndCategory(String title, String category) {
    return postRepository.findByTitleAndCategory(title, category);
}


}
