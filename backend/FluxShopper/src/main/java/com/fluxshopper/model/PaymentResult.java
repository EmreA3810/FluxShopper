package com.fluxshopper.model;

import java.math.BigDecimal;
import java.time.Instant;

public class PaymentResult {
    private Long productId;
    private String productName;
    private String supplierCategory;
    private BigDecimal amount;
    private String policyId;
    private boolean success;
    private String txHash;
    private Instant timestamp;

    public PaymentResult() {
    }

    public PaymentResult(Long productId, String productName, String supplierCategory, BigDecimal amount,
            String policyId, boolean success, String txHash, Instant timestamp) {
        this.productId = productId;
        this.productName = productName;
        this.supplierCategory = supplierCategory;
        this.amount = amount;
        this.policyId = policyId;
        this.success = success;
        this.txHash = txHash;
        this.timestamp = timestamp;
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

    public String getSupplierCategory() {
        return supplierCategory;
    }

    public void setSupplierCategory(String supplierCategory) {
        this.supplierCategory = supplierCategory;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
