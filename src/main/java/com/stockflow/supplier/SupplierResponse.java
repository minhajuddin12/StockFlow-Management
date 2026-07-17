package com.stockflow.supplier;

public record SupplierResponse(
        Long id,
        String name,
        String contactEmail,
        String phone
) {
    public static SupplierResponse from(Supplier supplier) {
        return new SupplierResponse(
                supplier.getId(),
                supplier.getName(),
                supplier.getContactEmail(),
                supplier.getPhone()
        );
    }
}