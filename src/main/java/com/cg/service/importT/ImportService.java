package com.cg.service.importT;

import com.cg.model.Import;
import com.cg.service.IGeneralService;

public interface ImportService extends IGeneralService<Import> {

    Integer sumQuatityWithIdProduct(Long id);

}
