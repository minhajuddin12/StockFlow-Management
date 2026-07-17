package com.stockflow.purchaseorder;

import com.stockflow.config.AuthenticatedUser;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;
    private final AuthenticatedUser authenticatedUser;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService, AuthenticatedUser authenticatedUser) {
        this.purchaseOrderService = purchaseOrderService;
        this.authenticatedUser = authenticatedUser;
    }

    @GetMapping
    public List<PurchaseOrderResponse> list() {
        return purchaseOrderService.list(authenticatedUser.getCurrentCompanyId());
    }

    @PostMapping
    public ResponseEntity<PurchaseOrderResponse> create(@Valid @RequestBody PurchaseOrderRequest request) {
        return ResponseEntity.ok(purchaseOrderService.create(authenticatedUser.getCurrentCompanyId(), request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PurchaseOrderResponse> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String newStatus = body.get("status");
        return ResponseEntity.ok(purchaseOrderService.updateStatus(authenticatedUser.getCurrentCompanyId(), id, newStatus));
    }
}