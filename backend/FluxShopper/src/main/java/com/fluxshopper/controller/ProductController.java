package com.fluxshopper.controller;

import com.fluxshopper.model.Product;
import com.fluxshopper.service.ProductCatalogService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductCatalogService catalogService;

    public ProductController(ProductCatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return catalogService.getAllProducts();
    }
}
