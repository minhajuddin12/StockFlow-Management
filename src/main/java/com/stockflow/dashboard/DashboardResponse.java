package com.stockflow.dashboard;

import com.stockflow.stockmovement.StockMovementResponse;

import java.math.BigDecimal;
import java.util.List;

public record DashboardResponse(
        BigDecimal totalStockValue,
        long lowStockCount,
        long pendingPurchaseOrders,
        List<StockMovementResponse> recentMovements
) {}