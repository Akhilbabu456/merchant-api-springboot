package com.akhil.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.akhil.response.PostResponse;
import com.akhil.model.Category;
import com.akhil.model.Merchant;
import com.akhil.service.CategoryService;
import com.akhil.service.MerchantService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class PostController {

    @Autowired
    private CategoryService postService;

    @Autowired
    private MerchantService userService;

    @PostMapping("/api/post/create")
    public PostResponse createPost(@RequestBody Category post, @RequestHeader("Authorization") String jwt) throws Exception{
        try{
            Merchant user=userService.findUserByJwt(jwt);
            
            Category createdPost = postService.createPost(post, user);
            PostResponse res = new PostResponse();
            res.setPost(createdPost);
            res.setMessage("Post created successfully");
            return res;
        }catch(Exception e){
            PostResponse res = new PostResponse();
            res.setMessage(e.getMessage());
            return res;
        }
        
    }
    
    @GetMapping("/getposts")
    public List<Category> getAllPosts(
         @RequestParam(required = false) String searchTerm,
         @RequestParam(required = false) String content,
         @RequestParam(required = false) String category
         ) throws Exception{
            if (searchTerm != null && !searchTerm.isEmpty() && category != null && !category.isEmpty()) {
                return postService.findPostsByTitleAndCategory(searchTerm, category);
            } else if (searchTerm != null && !searchTerm.isEmpty()) {
                return postService.findPostsByTitle(searchTerm);
            } else if (content != null && !content.isEmpty()) {
                return postService.findPostsByContent(content);
            } else if (category != null && !category.isEmpty()) {
                return postService.findPostsByCategory(category);
            } else {
                return postService.findAllPost();
            }
    }
    
    @GetMapping("/getpost/{id}")
    public Category findPostById(@PathVariable Long id) throws Exception{
        
        
        Category post = postService.findPostById(id);
        return post;
    }

    @DeleteMapping("/api/post/{id}")
    public String deletePost(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception{
        
        
        postService.deletePost(id);
        return "Post deleted successfully";
    }

    @PutMapping("/api/post/{id}")
    public PostResponse updatePost(@RequestHeader("Authorization") String jwt, @RequestBody Category post, @PathVariable Long id) throws Exception{
        try{
            Category updatedPost = postService.updatePost(post, id);
            PostResponse res = new PostResponse();
            res.setPost(updatedPost);
            res.setMessage("Post updated successfully");
            return res;
        }catch(Exception e){
            PostResponse res = new PostResponse();
            res.setMessage(e.getMessage());
            return res;
        }
        
    }

    @GetMapping("/api/post/getuserpost/{id}")
    public List<Category> getPostByUsers(@PathVariable Long id) throws Exception {
        List<Category> userPost = postService.getPostByUserId(id);
        return userPost;
    }
    
   
}
