package com.fluxshopper.service;

import com.fluxshopper.model.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductMatcherService {

    private final ProductCatalogService catalogService;

    public ProductMatcherService(ProductCatalogService catalogService) {
        this.catalogService = catalogService;
    }

    public List<Product> matchByPurposeAndBudget(String purpose, BigDecimal maxBudget) {
        String normalizedPurpose = purpose == null ? "" : purpose.toLowerCase(Locale.ROOT);
        List<Product> allProducts = catalogService.getAllProducts();

        Map<String, List<Long>> templates = new HashMap<>();
        templates.put("conference", List.of(3L, 6L, 11L));
        templates.put("konferans", List.of(3L, 6L, 11L));
        templates.put("casual", List.of(1L, 5L, 9L));
        templates.put("formal", List.of(2L, 4L, 10L));
        templates.put("spor", List.of(1L, 5L, 9L));
        templates.put("sport", List.of(1L, 5L, 9L));

        List<Long> chosenTemplate = templates.entrySet().stream()
                .filter(entry -> normalizedPurpose.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(List.of(1L, 5L, 9L));

        Set<Long> selectedIds = new LinkedHashSet<>(chosenTemplate);
        List<Product> selected = allProducts.stream()
                .filter(p -> selectedIds.contains(p.getId()))
                .collect(Collectors.toCollection(ArrayList::new));

        BigDecimal currentTotal = sum(selected);
        if (currentTotal.compareTo(maxBudget) > 0) {
            return fitToBudget(selected, maxBudget);
        }

        List<Product> extras = allProducts.stream()
                .filter(p -> !selectedIds.contains(p.getId()))
                .filter(p -> purposeMatches(p, normalizedPurpose))
                .sorted((a, b) -> a.getPrice().compareTo(b.getPrice()))
                .toList();

        for (Product candidate : extras) {
            BigDecimal nextTotal = currentTotal.add(candidate.getPrice());
            if (nextTotal.compareTo(maxBudget) <= 0) {
                selected.add(candidate);
                currentTotal = nextTotal;
            }
        }

        return selected;
    }

    private List<Product> fitToBudget(List<Product> products, BigDecimal maxBudget) {
        List<Product> sorted = new ArrayList<>(products);
        sorted.sort((a, b) -> a.getPrice().compareTo(b.getPrice()));

        List<Product> result = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for (Product p : sorted) {
            BigDecimal nextTotal = total.add(p.getPrice());
            if (nextTotal.compareTo(maxBudget) <= 0) {
                result.add(p);
                total = nextTotal;
            }
        }
        return result;
    }

    private boolean purposeMatches(Product product, String normalizedPurpose) {
        if (normalizedPurpose.isBlank()) {
            return true;
        }
        return product.getTags().stream()
                .map(t -> t.toLowerCase(Locale.ROOT))
                .anyMatch(normalizedPurpose::contains);
    }

    public BigDecimal sum(List<Product> products) {
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
