package com.fluxshopper.model;

import java.time.LocalDateTime;
import java.util.List;

public class ViewHistory {
    private Long productId;
    private String productName;
    private String category;
    private String price;
    private String currency;
    private List<String> tags;
    private String purpose;
    private LocalDateTime viewedAt;

    public ViewHistory() {
    }

    public ViewHistory(Long productId, String productName, String category, String price,
            String currency, List<String> tags, String purpose) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.currency = currency;
        this.tags = tags;
        this.purpose = purpose;
        this.viewedAt = LocalDateTime.now();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public LocalDateTime getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(LocalDateTime viewedAt) {
        this.viewedAt = viewedAt;
    }
}
