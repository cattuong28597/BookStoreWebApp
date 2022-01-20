package com.cg.repository;

import com.cg.model.Product;
import com.cg.model.dto.IProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query("SELECT " +
            "pm.product.id AS id, " +
            "pm.product.name AS name, " +
            "pm.product.description AS description, " +
            "pm.id AS fileId, " +
            "pm.fileName AS fileName, " +
            "pm.fileFolder AS fileFolder, " +
            "pm.fileUrl AS fileUrl, " +
            "pm.fileType AS fileType " +
            "FROM ProductImage pm " +
            "ORDER BY pm.product.ts ASC"
    )
    Iterable<IProductDTO> findAllIProductDTO();


    @Query("SELECT " +
            "pm.product.id AS id, " +
            "pm.product.name AS name, " +
            "pm.product.description AS description, " +
            "pm.id AS fileId, " +
            "pm.fileName AS fileName, " +
            "pm.fileFolder AS fileFolder, " +
            "pm.fileUrl AS fileUrl, " +
            "pm.fileType AS fileType " +
            "FROM ProductImage pm " +
            "WHERE pm.product.id = :id"
    )
    IProductDTO findIProductDTOById(@Param("id") Long id);

    Product findBySlug(String slug);

    @Query(value = "SELECT p FROM Product p WHERE p.categoryGroup.category.name= ?1 AND p.deleted = false")
    List<Product> findAllByCategoryName(String name);

    @Query(value = "SELECT p FROM Product p WHERE p.categoryGroup.slug= ?1 AND p.deleted = false")
    List<Product> findAllByCategoryGroupSlug(String slug);

    @Query(value = "SELECT p FROM Product p WHERE p.deleted = false")
    List<Product> findAllProductIsExist();
}
