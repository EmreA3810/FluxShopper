package com.fluxshopper.controller;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

    @PostMapping("/pay/{category}")
    public Map<String, Object> pay(@PathVariable String category, @RequestBody Map<String, Object> request) {
        return Map.of(
                "success", true,
                "supplierCategory", category,
                "amount", request.get("amount"),
                "policyId", request.get("policyId"),
                "txHash", "0x" + UUID.randomUUID().toString().replace("-", "").substring(0, 32),
                "timestamp", Instant.now().toString());
    }
}
