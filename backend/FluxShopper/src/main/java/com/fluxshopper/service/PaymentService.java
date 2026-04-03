package com.fluxshopper.service;

import com.fluxshopper.model.PaymentResult;
import com.fluxshopper.model.Product;
import java.time.Instant;
import java.util.Locale;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public PaymentResult pay(Product product, String policyId) {
        String supplierCategory = normalizeCategory(product.getCategory());
        String txHash = "0x" + UUID.randomUUID().toString().replace("-", "")
                + Long.toHexString(Instant.now().toEpochMilli());
        Instant timestamp = Instant.now();

        PaymentResult result = new PaymentResult();
        result.setProductId(product.getId());
        result.setProductName(product.getName());
        result.setSupplierCategory(supplierCategory);
        result.setAmount(product.getPrice());
        result.setPolicyId(policyId);
        result.setSuccess(true);
        result.setTxHash(txHash.substring(0, Math.min(txHash.length(), 42)));
        result.setTimestamp(timestamp);
        return result;
    }

    private String normalizeCategory(String category) {
        if (category == null || category.isBlank()) {
            return "general";
        }
        return category.toLowerCase(Locale.ROOT).replace(" ", "-");
    }
}
