package com.stockflow.stockmovement;

import com.stockflow.product.Product;
import com.stockflow.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;

    public StockMovementService(StockMovementRepository stockMovementRepository,
                                ProductRepository productRepository) {
        this.stockMovementRepository = stockMovementRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public StockMovementResponse createAdjustment(Long companyId, StockMovementRequest request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (!product.getCompany().getId().equals(companyId)) {
            throw new IllegalArgumentException("Product not found");
        }

        int delta = "IN".equalsIgnoreCase(request.type()) ? request.quantity() : -request.quantity();
        int newQuantity = product.getQuantityOnHand() + delta;

        if (newQuantity < 0) {
            throw new IllegalArgumentException("Adjustment would result in negative stock");
        }

        product.setQuantityOnHand(newQuantity);
        productRepository.save(product);

        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setType(request.type().toUpperCase());
        movement.setQuantity(request.quantity());
        movement.setReason(request.reason());

        return StockMovementResponse.from(stockMovementRepository.save(movement));
    }

    public List<StockMovementResponse> historyForProduct(Long companyId, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (!product.getCompany().getId().equals(companyId)) {
            throw new IllegalArgumentException("Product not found");
        }

        return stockMovementRepository.findByProductIdOrderByCreatedAtDesc(productId)
                .stream()
                .map(StockMovementResponse::from)
                .toList();
    }
}