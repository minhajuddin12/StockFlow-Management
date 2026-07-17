package com.stockflow.purchaseorder;

import com.stockflow.company.Company;
import com.stockflow.product.Product;
import com.stockflow.product.ProductRepository;
import com.stockflow.stockmovement.StockMovement;
import com.stockflow.stockmovement.StockMovementRepository;
import com.stockflow.supplier.Supplier;
import com.stockflow.supplier.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository,
                                SupplierRepository supplierRepository,
                                ProductRepository productRepository,
                                StockMovementRepository stockMovementRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.supplierRepository = supplierRepository;
        this.productRepository = productRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    public List<PurchaseOrderResponse> list(Long companyId) {
        return purchaseOrderRepository.findByCompanyId(companyId)
                .stream()
                .map(PurchaseOrderResponse::from)
                .toList();
    }

    @Transactional
    public PurchaseOrderResponse create(Long companyId, PurchaseOrderRequest request) {
        Supplier supplier = supplierRepository.findById(request.supplierId())
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));

        PurchaseOrder po = new PurchaseOrder();
        po.setStatus("DRAFT");
        po.setSupplier(supplier);
        Company companyRef = new Company();
        companyRef.setId(companyId);
        po.setCompany(companyRef);

        for (PurchaseOrderItemRequest itemReq : request.items()) {
            Product product = productRepository.findById(itemReq.productId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + itemReq.productId()));

            PurchaseOrderItem item = new PurchaseOrderItem();
            item.setPurchaseOrder(po);
            item.setProduct(product);
            item.setQuantity(itemReq.quantity());
            item.setUnitCost(itemReq.unitCost());
            po.getItems().add(item);
        }

        return PurchaseOrderResponse.from(purchaseOrderRepository.save(po));
    }

    @Transactional
    public PurchaseOrderResponse updateStatus(Long companyId, Long poId, String newStatus) {
        PurchaseOrder po = getOwned(companyId, poId);

        boolean becomingReceived = "RECEIVED".equals(newStatus) && !"RECEIVED".equals(po.getStatus());

        po.setStatus(newStatus);

        if (becomingReceived) {
            for (PurchaseOrderItem item : po.getItems()) {
                Product product = item.getProduct();
                product.setQuantityOnHand(product.getQuantityOnHand() + item.getQuantity());
                productRepository.save(product);

                StockMovement movement = new StockMovement();
                movement.setProduct(product);
                movement.setType("IN");
                movement.setQuantity(item.getQuantity());
                movement.setReason("Purchase Order #" + po.getId() + " received");
                stockMovementRepository.save(movement);
            }
        }

        return PurchaseOrderResponse.from(purchaseOrderRepository.save(po));
    }

    private PurchaseOrder getOwned(Long companyId, Long poId) {
        PurchaseOrder po = purchaseOrderRepository.findById(poId)
                .orElseThrow(() -> new IllegalArgumentException("Purchase order not found"));
        if (!po.getCompany().getId().equals(companyId)) {
            throw new IllegalArgumentException("Purchase order not found");
        }
        return po;
    }
}