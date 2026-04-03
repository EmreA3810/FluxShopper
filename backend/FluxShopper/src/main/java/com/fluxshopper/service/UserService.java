package com.fluxshopper.service;

import com.fluxshopper.model.User;
import com.fluxshopper.model.ViewHistory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    // Simple in-memory storage (In production, use a database)
    private final Map<String, User> userDatabase = new HashMap<>();

    public User getOrCreateUser(String walletAddress) {
        return userDatabase.computeIfAbsent(walletAddress,
                address -> new User(address, "User " + address.substring(0, 6), ""));
    }

    public List<ViewHistory> getUserViewHistory(String walletAddress, int limit) {
        User user = getOrCreateUser(walletAddress);
        return user.getViewHistory().stream().limit(limit).collect(Collectors.toList());
    }

    public ViewHistory addToViewHistory(String walletAddress, ViewHistory history) {
        User user = getOrCreateUser(walletAddress);
        user.addToViewHistory(history);
        return history;
    }

    public User updateUserProfile(String walletAddress, Map<String, String> updates) {
        User user = getOrCreateUser(walletAddress);

        if (updates.containsKey("displayName")) {
            user.setDisplayName(updates.get("displayName"));
        }
        if (updates.containsKey("email")) {
            user.setEmail(updates.get("email"));
        }

        return user;
    }

    public void updateUserPurchaseStats(String walletAddress, double amount) {
        User user = getOrCreateUser(walletAddress);
        user.incrementTotalPurchases();
        user.addToTotalSpent(amount);
    }
}
