package com.stockflow.stockmovement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record StockMovementRequest(
        @NotNull Long productId,
        @NotBlank String type, // "IN" or "OUT"
        @Positive Integer quantity,
        String reason
) {}