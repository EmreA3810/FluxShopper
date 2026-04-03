package com.fluxshopper.controller;

import com.fluxshopper.model.ShoppingReceipt;
import com.fluxshopper.model.ShoppingRequest;
import com.fluxshopper.service.ShoppingService;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shopping")
public class ShoppingController {

    private final ShoppingService shoppingService;

    public ShoppingController(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok", "service", "fluxshopper-backend");
    }

    @PostMapping("/checkout")
    public ShoppingReceipt checkout(@RequestBody ShoppingRequest request) {
        return shoppingService.checkout(request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationError(IllegalArgumentException ex) {
        return Map.of("error", ex.getMessage());
    }
}
