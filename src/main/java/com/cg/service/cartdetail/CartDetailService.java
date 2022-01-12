package com.cg.service.cartdetail;

import com.cg.model.CartDetail;
import com.cg.service.IGeneralService;

import java.util.List;

public interface CartDetailService extends IGeneralService<CartDetail> {
    List<CartDetail> findCartDetailByCartAndDeletedIsFalse(Long id_cart);
}
