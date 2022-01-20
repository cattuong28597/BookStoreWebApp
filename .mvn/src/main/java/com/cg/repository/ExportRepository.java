package com.cg.repository;

import com.cg.model.Export;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExportRepository extends JpaRepository<Export, Long> {
    @Query(value = "SELECT SUM(quantity) from `c07-spb-book-store`.export_products WHERE export_products.product_id = :id", nativeQuery = true)
    int sumQuatityWithIdProduct(@Param("id") Long id_product);
}