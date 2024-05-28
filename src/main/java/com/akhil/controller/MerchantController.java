package com.akhil.controller;

import com.akhil.service.MerchantDTO;
import com.akhil.model.Merchant;
import com.akhil.response.MerchantResponse;
import com.akhil.service.MerchantService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;





@RestController
@RequestMapping("/api/user")
public class MerchantController {

  @Autowired
  private MerchantService merchantService;

 
  @GetMapping("/getusers")
  public List<Merchant> findAllUser(@RequestHeader("Authorization") String jwt) throws Exception{

    List<Merchant> user = merchantService.findAllUsers();
    return user;
  } 
  
  @GetMapping("/{userId}")
  public MerchantResponse findUserById(@RequestHeader("Authorization") String jwt, @PathVariable Long userId) throws Exception {
   try{
     Merchant user = merchantService.findUserById(userId);  
     MerchantResponse res = new MerchantResponse();
     res.setUser(user);
     res.setMessage("User found");
     return res;
   }catch(Exception e){
    MerchantResponse res = new MerchantResponse();
    res.setMessage(e.getMessage());
    return res;
   }
  }

  @PostMapping("/add")
  public ResponseEntity<MerchantResponse> createUser(@RequestHeader("Authorization") String jwt, @RequestBody MerchantDTO userDto) {
       try{
        Merchant newUser = merchantService.createUser(userDto);
        MerchantResponse res = new MerchantResponse();
        res.setUser(newUser);
        res.setMessage("Created successfully");
        return ResponseEntity.ok(res);
       }catch(Exception e){
        MerchantResponse res = new MerchantResponse();
        res.setMessage(e.getMessage());
        return ResponseEntity.status(400).body(res);
       }
  }

  @DeleteMapping("/delete/{id}")
  public MerchantResponse deleteUserById(@RequestHeader("Authorization") String jwt, @PathVariable Long id) {
      try{
        merchantService.deleteUserById(id);
        MerchantResponse res = new MerchantResponse();
        res.setMessage("Deleted successfully");
        return res;
      }catch(Exception e){
        MerchantResponse res = new MerchantResponse();
        res.setMessage(e.getMessage());
        return res;
      }
  }
  
  
  
 
}
