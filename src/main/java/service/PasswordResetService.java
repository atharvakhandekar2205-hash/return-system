package com.example.returnsystem.service;

import com.example.returnsystem.model.PasswordResetToken;
import com.example.returnsystem.model.Seller;
import com.example.returnsystem.repository.PasswordResetTokenRepository;
import com.example.returnsystem.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String createResetToken(String email) {
        Seller seller = sellerRepository.findByEmail(email);

        if (seller == null) {
            return "Email not registered";
        }

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setEmail(email);
        resetToken.setToken(token);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
        resetToken.setUsed(false);

        tokenRepository.save(resetToken);

        String resetLink = "http://localhost:8080/reset-password.html?token=" + token;
        emailService.sendResetEmail(email, resetLink);

        return "Reset link sent to email";
    }

    public String resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token);

        if (resetToken == null) {
            return "Invalid token";
        }

        if (resetToken.isUsed()) {
            return "Token already used";
        }

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return "Token expired";
        }

        Seller seller = sellerRepository.findByEmail(resetToken.getEmail());
        if (seller == null) {
            return "Seller not found";
        }

        seller.setPassword(passwordEncoder.encode(newPassword));
        sellerRepository.save(seller);

        resetToken.setUsed(true);
        tokenRepository.save(resetToken);

        return "Password reset successful";
    }
}
