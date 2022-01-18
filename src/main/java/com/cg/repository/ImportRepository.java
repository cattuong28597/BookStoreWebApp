package com.cg.repository;

import com.cg.model.Import;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImportRepository extends JpaRepository<Import, Long> {
    @Query(value = "SELECT SUM(quantity) from `c07-spb-book-store`.import_products WHERE import_products.product_id = :id", nativeQuery = true)
    int sumQuatityWithIdProduct(@Param("id") Long id_product);
}
