package com.fluxshopper.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String walletAddress;
    private String displayName;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ViewHistory> viewHistory;
    private int totalPurchases;
    private double totalSpent;

    public User() {
        this.viewHistory = new ArrayList<>();
        this.totalPurchases = 0;
        this.totalSpent = 0.0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public User(String walletAddress, String displayName, String email) {
        this.walletAddress = walletAddress;
        this.displayName = displayName;
        this.email = email;
        this.viewHistory = new ArrayList<>();
        this.totalPurchases = 0;
        this.totalSpent = 0.0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        this.updatedAt = LocalDateTime.now();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<ViewHistory> getViewHistory() {
        return viewHistory;
    }

    public void addToViewHistory(ViewHistory history) {
        this.viewHistory.add(0, history); // Add to top
        this.updatedAt = LocalDateTime.now();
    }

    public int getTotalPurchases() {
        return totalPurchases;
    }

    public void setTotalPurchases(int totalPurchases) {
        this.totalPurchases = totalPurchases;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(double totalSpent) {
        this.totalSpent = totalSpent;
    }

    public void incrementTotalPurchases() {
        this.totalPurchases++;
        this.updatedAt = LocalDateTime.now();
    }

    public void addToTotalSpent(double amount) {
        this.totalSpent += amount;
        this.updatedAt = LocalDateTime.now();
    }
}
