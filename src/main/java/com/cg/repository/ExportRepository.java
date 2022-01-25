package com.cg.repository;

import com.cg.model.Export;
import com.cg.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExportRepository extends JpaRepository<Export, Long> {

    @Query("SELECT SUM(e.quantity) FROM Export e WHERE e.productExport = ?1")
    int SumOfExportQuantityByProduct(Product product);
}
