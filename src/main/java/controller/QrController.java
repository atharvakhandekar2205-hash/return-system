package com.example.returnsystem.controller;

import com.example.returnsystem.model.Return;
import com.example.returnsystem.service.QrService;
import com.example.returnsystem.service.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Map;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

@RestController
@RequestMapping("/api/qr")
@CrossOrigin
public class QrController {

    @Autowired
    private QrService qrService;

    @Autowired
    private ReturnService returnService;

    @PostMapping("/generate")
    public String generateQR(@RequestBody String text) {
        return qrService.generateQR(text);
    }

    @PostMapping("/scan")
    public String scanQR(@RequestParam("file") MultipartFile file) throws Exception {
        BufferedImage image = ImageIO.read(file.getInputStream());

        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        Result result = new MultiFormatReader().decode(bitmap);

        return result.getText();
    }

    @PostMapping("/save-scanned")
    public Return saveScannedQr(@RequestBody Map<String, String> qrData) {
        return returnService.saveScannedReturn(qrData);
    }
}