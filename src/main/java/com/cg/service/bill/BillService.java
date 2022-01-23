package com.cg.service.bill;

import com.cg.model.Bill;
import com.cg.model.Order;
import com.cg.model.dto.IBillDTO;
import com.cg.service.IGeneralService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BillService extends IGeneralService<Bill> {
    List<Bill> findAllByDeletedIsFalse() ;

    Optional<Bill> findByOrder(Order order);

    List<Bill> findAllByDeletedIsFalseAndPaidIsTrueAndCreatedAtIsBetween(Date startDate, Date endDate);

   List<IBillDTO> findIBillDTOWithCreateAt(Date startDate, Date endDate) ;
}
