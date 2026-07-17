package com.stockflow.supplier;

import com.stockflow.company.Company;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<SupplierResponse> list(Long companyId) {
        return supplierRepository.findByCompanyId(companyId)
                .stream()
                .map(SupplierResponse::from)
                .toList();
    }

    public SupplierResponse create(Long companyId, SupplierRequest request) {
        Supplier supplier = new Supplier();
        supplier.setName(request.name());
        supplier.setContactEmail(request.contactEmail());
        supplier.setPhone(request.phone());
        Company companyRef = new Company();
        companyRef.setId(companyId);
        supplier.setCompany(companyRef);
        return SupplierResponse.from(supplierRepository.save(supplier));
    }
}