package com.cg.service.product;

import com.cg.model.Product;
import com.cg.service.IGeneralService;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductService extends IGeneralService<Product> {

    Boolean existsBySlugEquals(String slug);

    Boolean existsBySlugEqualsAndIdIsNot(String slug, long id);

    @Query("select c from Product c where c.slug = ?1 and c.id <> ?2")
    Optional<Product> findBySlugAndIdIsNot(String slug, Long id);
}
