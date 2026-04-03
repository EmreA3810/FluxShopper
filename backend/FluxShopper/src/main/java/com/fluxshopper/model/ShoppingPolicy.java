package com.fluxshopper.model;

import java.math.BigDecimal;
import java.time.Instant;

public class ShoppingPolicy {
    private String policyId;
    private BigDecimal maxBudget;
    private String currency;
    private String purpose;
    private String agentAddress;
    private Instant expiresAt;
    private String signature;

    public ShoppingPolicy() {
    }

    public ShoppingPolicy(String policyId, BigDecimal maxBudget, String currency, String purpose,
            String agentAddress, Instant expiresAt, String signature) {
        this.policyId = policyId;
        this.maxBudget = maxBudget;
        this.currency = currency;
        this.purpose = purpose;
        this.agentAddress = agentAddress;
        this.expiresAt = expiresAt;
        this.signature = signature;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public BigDecimal getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(BigDecimal maxBudget) {
        this.maxBudget = maxBudget;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getAgentAddress() {
        return agentAddress;
    }

    public void setAgentAddress(String agentAddress) {
        this.agentAddress = agentAddress;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
