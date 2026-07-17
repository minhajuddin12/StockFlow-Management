package com.stockflow.stockmovement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    List<StockMovement> findByProductIdOrderByCreatedAtDesc(Long productId);

    @Query("SELECT sm FROM StockMovement sm WHERE sm.product.company.id = :companyId ORDER BY sm.createdAt DESC")
    List<StockMovement> findRecentByCompanyId(@Param("companyId") Long companyId);
}