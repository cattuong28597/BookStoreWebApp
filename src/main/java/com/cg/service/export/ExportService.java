package com.cg.service.export;

import com.cg.model.Export;
import com.cg.service.IGeneralService;

public interface ExportService extends IGeneralService<Export> {
    Integer sumQuatityWithIdProduct(Long id);
}
