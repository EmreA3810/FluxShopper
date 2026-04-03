package com.fluxshopper.service;

import com.fluxshopper.model.ShoppingPolicy;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.HexFormat;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PolicyEngineService {

    public ShoppingPolicy createPolicy(BigDecimal budget, String currency, String purpose, String userWalletAddress) {
        String policyId = "POL-" + UUID.randomUUID();
        Instant expiresAt = Instant.now().plusSeconds(3600);

        // Use user's wallet address or default agent address
        String agentAddress = (userWalletAddress != null && !userWalletAddress.isEmpty())
                ? userWalletAddress
                : "0xFluxAgent" + UUID.randomUUID().toString().substring(0, 8);

        String signingPayload = policyId + "|" + budget + "|" + currency + "|" + purpose + "|" + agentAddress + "|"
                + expiresAt;
        String signature = sign(signingPayload);

        ShoppingPolicy policy = new ShoppingPolicy();
        policy.setPolicyId(policyId);
        policy.setMaxBudget(budget);
        policy.setCurrency(currency);
        policy.setPurpose(purpose);
        policy.setAgentAddress(agentAddress);
        policy.setExpiresAt(expiresAt);
        policy.setSignature(signature);
        return policy;
    }

    private String sign(String payload) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(payload.getBytes(StandardCharsets.UTF_8));
            return "SIG-" + HexFormat.of().formatHex(hash).substring(0, 24);
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to sign policy payload", ex);
        }
    }
}
