package com.cg.service.voucher;

import com.cg.model.Voucher;
import com.cg.service.IGeneralService;

import java.util.List;

public interface VoucherService extends IGeneralService<Voucher> {
    List<Voucher> findVoucherByDeletedIsFalse() ;
}
