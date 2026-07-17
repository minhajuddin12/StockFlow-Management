package com.stockflow.stockmovement;

import com.stockflow.config.AuthenticatedUser;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-movements")
public class StockMovementController {

    private final StockMovementService stockMovementService;
    private final AuthenticatedUser authenticatedUser;

    public StockMovementController(StockMovementService stockMovementService, AuthenticatedUser authenticatedUser) {
        this.stockMovementService = stockMovementService;
        this.authenticatedUser = authenticatedUser;
    }

    @PostMapping
    public ResponseEntity<StockMovementResponse> create(@Valid @RequestBody StockMovementRequest request) {
        return ResponseEntity.ok(stockMovementService.createAdjustment(authenticatedUser.getCurrentCompanyId(), request));
    }

    @GetMapping("/product/{productId}")
    public List<StockMovementResponse> history(@PathVariable Long productId) {
        return stockMovementService.historyForProduct(authenticatedUser.getCurrentCompanyId(), productId);
    }
}