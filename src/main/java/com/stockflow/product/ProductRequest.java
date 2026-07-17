package com.stockflow.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank String sku,
        @NotBlank String name,
        String category,
        @NotNull @Positive BigDecimal unitPrice,
        @PositiveOrZero Integer quantityOnHand,
        @PositiveOrZero Integer reorderLevel
) {}