package com.cg.repository;

import com.cg.model.Bill;
import com.cg.model.Order;
import com.cg.model.dto.IBillDTO;
import com.cg.model.dto.IProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findAllByDeletedIsFalse() ;

    Optional<Bill> findByOrder(Order order);


    List<Bill> findAllByDeletedIsFalseAndPaidIsTrueAndCreatedAtIsBetween(Date startDay, Date endDay) ;



    @Query("SELECT " +
            "b.id AS id, " +
            "b.createdAt AS createAt, " +
            "b.order.customer.name AS customerName, " +
//            "b.voucher AS voucher, " +
            "b.totalAmount AS totalAmount " +
            "FROM Bill b " +
            "WHERE b.deleted = false AND b.paid = true " +
            "AND b.createdAt >= :startDate and b.createdAt < :endDate "
    )
    List<IBillDTO> findIBillDTOWithCreateAt(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
