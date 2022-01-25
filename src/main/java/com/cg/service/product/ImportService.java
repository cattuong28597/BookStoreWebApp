package com.cg.service.product;

import com.cg.model.Import;
import com.cg.model.Product;
import com.cg.service.IGeneralService;

public interface ImportService extends IGeneralService<Import> {

    int SumOfImportQuantityByProduct(Product product);
}
