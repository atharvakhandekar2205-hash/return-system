package com.example.returnsystem.service;

import com.example.returnsystem.model.Return;
import com.example.returnsystem.repository.ReturnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReturnService {

    @Autowired
    private ReturnRepository repository;

    @Autowired
    private QrService qrService;

    public Return processReturn(Return r) {

        double profit = r.getCurrentPrice() - r.getOriginalPrice();
        r.setProfit(profit);

        double percent = (profit / r.getOriginalPrice()) * 100;
        r.setProfitPercent(percent);

        if (profit < 0) {
            r.setStatus("LOSS ❌");
        } else {
            r.setStatus("OK ✅");
        }

        // QR Data
        String qrData = "Order: " + r.getOrderId() + " | Profit: " + profit;
        String qrBase64 = qrService.generateQR(qrData);
        r.setQrCode(qrBase64);

        return repository.save(r);
    }

    public List<Return> getAll() {
        return repository.findAll();
    }

    // 🔥 BUSINESS ANALYTICS
    public Map<String, Object> getStats() {

        List<Return> list = repository.findAll();

        double totalLoss = 0;
        Map<String, Integer> productMap = new HashMap<>();
        Map<String, Integer> reasonMap = new HashMap<>();

        for (Return r : list) {

            if (r.getProfit() < 0) {
                totalLoss += Math.abs(r.getProfit());
            }

            productMap.put(
                    r.getProduct(),
                    productMap.getOrDefault(r.getProduct(), 0) + 1
            );

            reasonMap.put(
                    r.getReason(),
                    reasonMap.getOrDefault(r.getReason(), 0) + 1
            );
        }

        String topProduct = "";
        int max = 0;

        for (String key : productMap.keySet()) {
            if (productMap.get(key) > max) {
                max = productMap.get(key);
                topProduct = key;
            }
        }

        String topReason = "";
        int maxReason = 0;

        for (String key : reasonMap.keySet()) {
            if (reasonMap.get(key) > maxReason) {
                maxReason = reasonMap.get(key);
                topReason = key;
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("totalReturns", list.size());
        result.put("totalLoss", totalLoss);
        result.put("topProduct", topProduct);
        result.put("topReason", topReason);

        return result;
    }
}