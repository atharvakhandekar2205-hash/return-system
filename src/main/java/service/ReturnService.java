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

    @Autowired(required = false)
    private QrService qrService;

    public Return processReturn(Return r) {

        double original = r.getOriginalPrice() != null ? r.getOriginalPrice() : 0;
        double current = r.getCurrentPrice() != null ? r.getCurrentPrice() : 0;

        double profit = current - original;
        r.setProfit(profit);

        r.setProfitPercent(original != 0 ? (profit / original) * 100 : 0);

        if (profit < 0) r.setStatus("LOSS ❌");
        else if (profit > 0) r.setStatus("PROFIT ✅");
        else r.setStatus("NO CHANGE");

        try {
            if (qrService != null) {
                String qrData = "Order: " + r.getOrderId() + " | Profit: " + profit;
                r.setQrCode(qrService.generateQR(qrData));
            } else {
                r.setQrCode("QR_DISABLED");
            }
        } catch (Exception e) {
            r.setQrCode("QR_ERROR");
        }

        return repository.save(r);
    }

    public List<Return> getAll() {
        return repository.findAll();
    }

    public Map<String, Object> getStats() {

        List<Return> list = repository.findAll();

        double totalLoss = 0;
        Map<String, Integer> productMap = new HashMap<>();
        Map<String, Integer> reasonMap = new HashMap<>();

        for (Return r : list) {

            if (r.getProfit() != null && r.getProfit() < 0) {
                totalLoss += Math.abs(r.getProfit());
            }

            String product = r.getProduct() != null ? r.getProduct() : "Unknown";
            productMap.put(product, productMap.getOrDefault(product, 0) + 1);

            String reason = r.getReason() != null ? r.getReason() : "Unknown";
            reasonMap.put(reason, reasonMap.getOrDefault(reason, 0) + 1);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("totalReturns", list.size());
        result.put("totalLoss", totalLoss);
        result.put("topProduct", Collections.max(productMap.entrySet(), Map.Entry.comparingByValue()).getKey());
        result.put("topReason", Collections.max(reasonMap.entrySet(), Map.Entry.comparingByValue()).getKey());

        return result;
    }
}