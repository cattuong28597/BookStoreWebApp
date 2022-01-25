package com.cg.repository;

import com.cg.model.Import;
import com.cg.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImportRepository extends JpaRepository<Import, Long> {

    @Query("SELECT SUM(e.quantity) FROM Import e WHERE e.productImport = ?1")
    int SumOfImportQuantityByProduct(Product product);
}
