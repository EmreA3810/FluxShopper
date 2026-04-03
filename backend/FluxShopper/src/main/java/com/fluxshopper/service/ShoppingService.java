package com.fluxshopper.service;

import com.fluxshopper.model.PaymentResult;
import com.fluxshopper.model.Product;
import com.fluxshopper.model.ShoppingPolicy;
import com.fluxshopper.model.ShoppingReceipt;
import com.fluxshopper.model.ShoppingRequest;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ShoppingService {

    private final ProductMatcherService matcherService;
    private final PolicyEngineService policyEngineService;
    private final PaymentService paymentService;

    public ShoppingService(
            ProductMatcherService matcherService,
            PolicyEngineService policyEngineService,
            PaymentService paymentService) {
        this.matcherService = matcherService;
        this.policyEngineService = policyEngineService;
        this.paymentService = paymentService;
    }

    public ShoppingReceipt checkout(ShoppingRequest request) {
        validateRequest(request);

        ShoppingPolicy policy = policyEngineService.createPolicy(
                request.getBudget(),
                request.getCurrency(),
                request.getPurpose(),
                request.getWalletAddress()); // Add wallet address

        List<Product> selectedProducts = matcherService.matchByPurposeAndBudget(
                request.getPurpose(),
                request.getBudget());

        List<PaymentResult> payments = selectedProducts.stream()
                .map(product -> paymentService.pay(product, policy.getPolicyId()))
                .toList();

        BigDecimal totalSpent = matcherService.sum(selectedProducts);
        BigDecimal remainingBudget = request.getBudget().subtract(totalSpent);

        ShoppingReceipt receipt = new ShoppingReceipt();
        receipt.setReceiptId("RCPT-" + UUID.randomUUID());
        receipt.setPolicy(policy);
        receipt.setSelectedProducts(selectedProducts);
        receipt.setPayments(payments);
        receipt.setTotalSpent(totalSpent);
        receipt.setRemainingBudget(remainingBudget);
        receipt.setCreatedAt(Instant.now());
        return receipt;
    }

    private void validateRequest(ShoppingRequest request) {
        if (request == null || request.getBudget() == null) {
            throw new IllegalArgumentException("Budget is required");
        }
        if (request.getBudget().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Budget must be greater than zero");
        }
        if (request.getCurrency() == null || request.getCurrency().isBlank()) {
            request.setCurrency("USDC");
        }
        if (request.getPurpose() == null) {
            request.setPurpose("");
        }
    }
}
