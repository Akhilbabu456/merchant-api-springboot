package com.akhil.service;

import java.util.List;


import com.akhil.model.Merchant;

public interface MerchantService {
    
    public Merchant findUserById(Long userId) throws Exception;
    public List<Merchant>findAllUsers() throws Exception;
    public Merchant findUserByJwt(String jwt) throws Exception;
    public Merchant updateUserById(Merchant user,Long id) throws Exception;
    public void deleteUserById(Long id) throws Exception;
}
