package com.akhil.response;

import com.akhil.model.Merchant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MerchantResponse {
    private String message;
    private Merchant user;
}
