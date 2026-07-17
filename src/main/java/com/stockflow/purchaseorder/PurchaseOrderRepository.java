package com.stockflow.purchaseorder;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    List<PurchaseOrder> findByCompanyId(Long companyId);
    long countByCompanyIdAndStatusNot(Long companyId, String status);
}