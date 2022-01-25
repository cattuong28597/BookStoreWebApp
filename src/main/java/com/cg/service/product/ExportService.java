package com.cg.service.product;

import com.cg.model.Export;
import com.cg.model.Product;
import com.cg.service.IGeneralService;

public interface ExportService extends IGeneralService<Export> {

    int SumOfExportQuantityByProduct(Product product);
}
