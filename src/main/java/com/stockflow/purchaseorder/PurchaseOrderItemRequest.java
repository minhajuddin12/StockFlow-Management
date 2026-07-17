package com.stockflow.purchaseorder;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PurchaseOrderItemRequest(
        @NotNull Long productId,
        @Positive Integer quantity,
        @NotNull @Positive BigDecimal unitCost
) {}