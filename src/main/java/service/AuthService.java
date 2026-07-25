package com.example.returnsystem.service;

import com.example.returnsystem.model.Seller;
import com.example.returnsystem.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean login(String email, String password) {
        Seller seller = sellerRepository.findByEmail(email);

        return seller != null && passwordEncoder.matches(password, seller.getPassword());
    }
}
