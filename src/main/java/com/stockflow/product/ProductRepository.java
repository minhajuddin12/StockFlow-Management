package com.stockflow.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCompanyIdAndNameContainingIgnoreCase(
            Long companyId, String search, Pageable pageable);

    List<Product> findByCompanyIdAndQuantityOnHandLessThanEqual(
            Long companyId, Integer reorderLevel);

    @Query("SELECT COALESCE(SUM(p.unitPrice * p.quantityOnHand), 0) FROM Product p WHERE p.company.id = :companyId")
    BigDecimal totalStockValue(@Param("companyId") Long companyId);
}