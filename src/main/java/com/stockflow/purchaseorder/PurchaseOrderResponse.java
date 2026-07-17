package com.stockflow.purchaseorder;

import java.time.LocalDateTime;
import java.util.List;

public record PurchaseOrderResponse(
        Long id,
        Long supplierId,
        String supplierName,
        String status,
        LocalDateTime orderDate,
        List<PurchaseOrderItemResponse> items
) {
    public static PurchaseOrderResponse from(PurchaseOrder po) {
        return new PurchaseOrderResponse(
                po.getId(),
                po.getSupplier().getId(),
                po.getSupplier().getName(),
                po.getStatus(),
                po.getOrderDate(),
                po.getItems().stream().map(PurchaseOrderItemResponse::from).toList()
        );
    }
}