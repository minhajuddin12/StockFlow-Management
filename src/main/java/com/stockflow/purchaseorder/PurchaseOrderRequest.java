package com.stockflow.purchaseorder;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PurchaseOrderRequest(
        @NotNull Long supplierId,
        @NotEmpty @Valid List<PurchaseOrderItemRequest> items
) {}