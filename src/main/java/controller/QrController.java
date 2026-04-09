package com.example.returnsystem.controller;

import com.example.returnsystem.service.QrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qr")
@CrossOrigin
public class QrController {

    @Autowired
    private QrService qrService;

    // 🔥 Better to use POST (not GET for data generation)
    @PostMapping("/generate")
    public String generateQR(@RequestBody String text) {
        return qrService.generateQR(text);
    }
}