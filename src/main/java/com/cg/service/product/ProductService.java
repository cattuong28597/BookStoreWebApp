package com.cg.service.product;

import com.cg.model.Product;
import com.cg.service.IGeneralService;

public interface ProductService extends IGeneralService<Product> {

    Boolean existsBySlugEquals(String slug);

    Boolean existsBySlugEqualsAndIdIsNot(String slug, long id);
}
