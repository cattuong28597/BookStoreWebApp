package com.cg.service.importT;

import com.cg.model.Category;
import com.cg.model.Import;
import com.cg.service.IGeneralService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImportService extends IGeneralService<Import> {

    Integer sumQuatityWithIdProduct(Long id);

}
