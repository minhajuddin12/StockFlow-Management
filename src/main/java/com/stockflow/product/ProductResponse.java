package com.stockflow.product;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String sku,
        String name,
        String category,
        BigDecimal unitPrice,
        Integer quantityOnHand,
        Integer reorderLevel
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getCategory(),
                product.getUnitPrice(),
                product.getQuantityOnHand(),
                product.getReorderLevel()
        );
    }
}