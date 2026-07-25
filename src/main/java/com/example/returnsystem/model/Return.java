package com.example.returnsystem.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "return_data")
public class Return {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ NEW FIELD (for QR-based returns)
    private String returnId;

    private String orderId;
    private String product;

    private Double originalPrice;
    private Double currentPrice;
    private Double salePrice;

    private Double profit;
    private Double profitPercent;

    private String status;
    private String reason;

    private String sellerEmail;

    private LocalDateTime createdAt;

    @Lob
    private String qrCode;

    @PrePersist
    public void setDate() {
        this.createdAt = LocalDateTime.now();
    }

    // ===== GETTERS & SETTERS =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getReturnId() { return returnId; }
    public void setReturnId(String returnId) { this.returnId = returnId; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }

    public Double getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(Double originalPrice) { this.originalPrice = originalPrice; }

    public Double getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(Double currentPrice) { this.currentPrice = currentPrice; }

    public Double getSalePrice() { return salePrice; }
    public void setSalePrice(Double salePrice) { this.salePrice = salePrice; }

    public Double getProfit() { return profit; }
    public void setProfit(Double profit) { this.profit = profit; }

    public Double getProfitPercent() { return profitPercent; }
    public void setProfitPercent(Double profitPercent) { this.profitPercent = profitPercent; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getQrCode() { return qrCode; }
    public void setQrCode(String qrCode) { this.qrCode = qrCode; }

    public String getSellerEmail() { return sellerEmail; }
    public void setSellerEmail(String sellerEmail) { this.sellerEmail = sellerEmail; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}