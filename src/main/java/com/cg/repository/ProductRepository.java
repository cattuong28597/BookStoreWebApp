package com.cg.repository;

import com.cg.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Boolean existsBySlugEquals(String slug);

    Boolean existsBySlugEqualsAndIdIsNot(String slug, long id);
}
