package com.cg.service.product;

import com.cg.model.Product;
import com.cg.model.dto.IProductDTO;
import com.cg.model.dto.ProductDTO;
import com.cg.service.IGeneralService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService extends IGeneralService<Product> {

    Boolean existsBySlugEquals(String slug);

    Boolean existsBySlugEqualsAndIdIsNot(String slug, long id);

    Iterable<IProductDTO> findAllIProductDTO();

    IProductDTO findIProductDTOById(Long id);

    Product create(ProductDTO productDTO);

    void delete(Product product) throws IOException;

    Product findBySlug(String slug);

    List<Product> findAllByCategoryName(String name);

    List<Product> findAllByCategoryGroupSlug(String slug);

    List<Product> findAllProductIsExist();

}
