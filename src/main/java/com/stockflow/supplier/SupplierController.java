package com.stockflow.supplier;

import com.stockflow.config.AuthenticatedUser;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;
    private final AuthenticatedUser authenticatedUser;

    public SupplierController(SupplierService supplierService, AuthenticatedUser authenticatedUser) {
        this.supplierService = supplierService;
        this.authenticatedUser = authenticatedUser;
    }

    @GetMapping
    public List<SupplierResponse> list() {
        return supplierService.list(authenticatedUser.getCurrentCompanyId());
    }

    @PostMapping
    public ResponseEntity<SupplierResponse> create(@Valid @RequestBody SupplierRequest request) {
        return ResponseEntity.ok(supplierService.create(authenticatedUser.getCurrentCompanyId(), request));
    }
}