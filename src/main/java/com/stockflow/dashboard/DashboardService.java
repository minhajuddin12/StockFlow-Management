package com.stockflow.dashboard;

import com.stockflow.product.ProductRepository;
import com.stockflow.purchaseorder.PurchaseOrderRepository;
import com.stockflow.stockmovement.StockMovementRepository;
import com.stockflow.stockmovement.StockMovementResponse;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final ProductRepository productRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final StockMovementRepository stockMovementRepository;

    public DashboardService(ProductRepository productRepository,
                            PurchaseOrderRepository purchaseOrderRepository,
                            StockMovementRepository stockMovementRepository) {
        this.productRepository = productRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    public DashboardResponse summary(Long companyId) {
        var totalStockValue = productRepository.totalStockValue(companyId);
        var lowStockCount = productRepository
                .findByCompanyIdAndQuantityOnHandLessThanEqual(companyId, Integer.MAX_VALUE)
                .stream()
                .filter(p -> p.getQuantityOnHand() <= p.getReorderLevel())
                .count();
        var pendingPOs = purchaseOrderRepository.countByCompanyIdAndStatusNot(companyId, "RECEIVED");
        var recentMovements = stockMovementRepository.findRecentByCompanyId(companyId)
                .stream()
                .limit(10)
                .map(StockMovementResponse::from)
                .toList();

        return new DashboardResponse(totalStockValue, lowStockCount, pendingPOs, recentMovements);
    }
}