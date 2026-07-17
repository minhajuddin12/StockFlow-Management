package com.stockflow.product;

import com.stockflow.company.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductResponse> search(Long companyId, String search, Pageable pageable) {
        return productRepository
                .findByCompanyIdAndNameContainingIgnoreCase(companyId, search, pageable)
                .map(ProductResponse::from);
    }

    public ProductResponse create(Long companyId, ProductRequest request) {
        Product product = new Product();
        applyRequest(product, request);
        Company companyRef = new Company();
        companyRef.setId(companyId);
        product.setCompany(companyRef);
        return ProductResponse.from(productRepository.save(product));
    }

    public ProductResponse update(Long companyId, Long productId, ProductRequest request) {
        Product product = getOwned(companyId, productId);
        applyRequest(product, request);
        return ProductResponse.from(productRepository.save(product));
    }

    public void delete(Long companyId, Long productId) {
        Product product = getOwned(companyId, productId);
        productRepository.delete(product);
    }

    public List<ProductResponse> lowStock(Long companyId) {
        return productRepository.findByCompanyIdAndQuantityOnHandLessThanEqual(companyId, Integer.MAX_VALUE)
                .stream()
                .filter(p -> p.getQuantityOnHand() <= p.getReorderLevel())
                .map(ProductResponse::from)
                .toList();
    }

    private Product getOwned(Long companyId, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        if (!product.getCompany().getId().equals(companyId)) {
            throw new IllegalArgumentException("Product not found");
        }
        return product;
    }

    private void applyRequest(Product product, ProductRequest request) {
        product.setSku(request.sku());
        product.setName(request.name());
        product.setCategory(request.category());
        product.setUnitPrice(request.unitPrice());
        if (request.quantityOnHand() != null) product.setQuantityOnHand(request.quantityOnHand());
        if (request.reorderLevel() != null) product.setReorderLevel(request.reorderLevel());
        product.setCompany(product.getCompany());
    }
}