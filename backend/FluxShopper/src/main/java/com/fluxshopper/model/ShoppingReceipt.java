package com.fluxshopper.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class ShoppingReceipt {
    private String receiptId;
    private ShoppingPolicy policy;
    private List<Product> selectedProducts;
    private List<PaymentResult> payments;
    private BigDecimal totalSpent;
    private BigDecimal remainingBudget;
    private Instant createdAt;

    public ShoppingReceipt() {
    }

    public ShoppingReceipt(String receiptId, ShoppingPolicy policy, List<Product> selectedProducts,
            List<PaymentResult> payments, BigDecimal totalSpent, BigDecimal remainingBudget, Instant createdAt) {
        this.receiptId = receiptId;
        this.policy = policy;
        this.selectedProducts = selectedProducts;
        this.payments = payments;
        this.totalSpent = totalSpent;
        this.remainingBudget = remainingBudget;
        this.createdAt = createdAt;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public ShoppingPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(ShoppingPolicy policy) {
        this.policy = policy;
    }

    public List<Product> getSelectedProducts() {
        return selectedProducts;
    }

    public void setSelectedProducts(List<Product> selectedProducts) {
        this.selectedProducts = selectedProducts;
    }

    public List<PaymentResult> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentResult> payments) {
        this.payments = payments;
    }

    public BigDecimal getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(BigDecimal totalSpent) {
        this.totalSpent = totalSpent;
    }

    public BigDecimal getRemainingBudget() {
        return remainingBudget;
    }

    public void setRemainingBudget(BigDecimal remainingBudget) {
        this.remainingBudget = remainingBudget;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
