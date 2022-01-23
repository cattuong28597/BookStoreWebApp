package com.cg.model.dto;


import com.cg.model.Voucher;

import java.math.BigDecimal;

public interface IBillDTO {

    Long getId();
    String getCreateAt();
    String getCustomerName();
    Voucher getVoucher();
    BigDecimal getTotalAmount();

}
