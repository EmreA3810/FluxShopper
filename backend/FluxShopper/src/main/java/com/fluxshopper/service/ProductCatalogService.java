package com.fluxshopper.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluxshopper.model.Product;
import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductCatalogService {

    private final ObjectMapper objectMapper;
    private List<Product> products = new ArrayList<>();

    public ProductCatalogService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void loadCatalog() {
        try (InputStream inputStream = getClass().getResourceAsStream("/data/products.json")) {
            if (inputStream == null) {
                throw new IllegalStateException("products.json not found under resources/data");
            }
            this.products = objectMapper.readValue(inputStream, new TypeReference<List<Product>>() {
            });
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to load products catalog", ex);
        }
    }

    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(products);
    }
}
