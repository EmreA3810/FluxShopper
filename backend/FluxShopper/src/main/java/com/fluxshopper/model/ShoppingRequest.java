package com.fluxshopper.model;

import java.math.BigDecimal;

public class ShoppingRequest {
    private BigDecimal budget;
    private String purpose;
    private String currency = "USDC";
    private String walletAddress; // OWS wallet address

    public ShoppingRequest() {
    }

    public ShoppingRequest(BigDecimal budget, String purpose, String currency, String walletAddress) {
        this.budget = budget;
        this.purpose = purpose;
        this.currency = currency;
        this.walletAddress = walletAddress;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }
}
