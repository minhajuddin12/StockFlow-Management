package com.stockflow.product;

import com.stockflow.config.AuthenticatedUser;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final AuthenticatedUser authenticatedUser;

    public ProductController(ProductService productService, AuthenticatedUser authenticatedUser) {
        this.productService = productService;
        this.authenticatedUser = authenticatedUser;
    }

    @GetMapping
    public Page<ProductResponse> list(@RequestParam(defaultValue = "") String search, Pageable pageable) {
        return productService.search(authenticatedUser.getCurrentCompanyId(), search, pageable);
    }

    @GetMapping("/low-stock")
    public List<ProductResponse> lowStock() {
        return productService.lowStock(authenticatedUser.getCurrentCompanyId());
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.create(authenticatedUser.getCurrentCompanyId(), request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.update(authenticatedUser.getCurrentCompanyId(), id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(authenticatedUser.getCurrentCompanyId(), id);
        return ResponseEntity.noContent().build();
    }
}