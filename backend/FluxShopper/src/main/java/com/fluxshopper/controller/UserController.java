package com.fluxshopper.controller;

import com.fluxshopper.model.User;
import com.fluxshopper.model.ViewHistory;
import com.fluxshopper.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get or create user profile by wallet address
    @GetMapping("/profile/{walletAddress}")
    public ResponseEntity<?> getUserProfile(@PathVariable String walletAddress) {
        try {
            User user = userService.getOrCreateUser(walletAddress);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch user profile: " + e.getMessage()));
        }
    }

    // Get user's view history
    @GetMapping("/{walletAddress}/history")
    public ResponseEntity<?> getUserHistory(@PathVariable String walletAddress,
            @RequestParam(defaultValue = "10") int limit) {
        try {
            List<ViewHistory> history = userService.getUserViewHistory(walletAddress, limit);
            return ResponseEntity.ok(Map.of("walletAddress", walletAddress, "viewHistory", history, "count",
                    history.size()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch user history: " + e.getMessage()));
        }
    }

    // Add item to view history
    @PostMapping("/{walletAddress}/history")
    public ResponseEntity<?> addToViewHistory(@PathVariable String walletAddress,
            @RequestBody ViewHistory viewHistory) {
        try {
            ViewHistory savedHistory = userService.addToViewHistory(walletAddress, viewHistory);
            return ResponseEntity.ok(Map.of("message", "Item added to view history", "data", savedHistory));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to add to view history: " + e.getMessage()));
        }
    }

    // Update user profile
    @PostMapping("/profile/{walletAddress}")
    public ResponseEntity<?> updateUserProfile(@PathVariable String walletAddress,
            @RequestBody Map<String, String> updates) {
        try {
            User user = userService.updateUserProfile(walletAddress, updates);
            return ResponseEntity.ok(Map.of("message", "Profile updated successfully", "user", user));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update profile: " + e.getMessage()));
        }
    }

    // Get user statistics
    @GetMapping("/{walletAddress}/stats")
    public ResponseEntity<?> getUserStats(@PathVariable String walletAddress) {
        try {
            User user = userService.getOrCreateUser(walletAddress);
            Map<String, Object> stats = new HashMap<>();
            stats.put("walletAddress", walletAddress);
            stats.put("totalPurchases", user.getTotalPurchases());
            stats.put("totalSpent", user.getTotalSpent());
            stats.put("totalViews", user.getViewHistory().size());
            stats.put("joinedAt", user.getCreatedAt());
            stats.put("lastActive", user.getUpdatedAt());
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch user stats: " + e.getMessage()));
        }
    }
}
