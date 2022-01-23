package com.cg.model.dto;

import com.cg.model.Voucher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BillDTO {
    private Long id;

    private Long orderId;

    private Long voucherId ;

    private String voucherName ;

    private String createAt ;

    private String date ;

    private String time ;

    private String customerName ;

    private BigDecimal totalAmount ;

}
