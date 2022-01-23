package com.cg.model;


import com.cg.model.dto.BillDTO;
import com.cg.utils.AppUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bills")
public class Bill extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "voucher_id", referencedColumnName = "id")
    private Voucher voucher;

    @Digits(integer = 12, fraction = 2)
    @Column(name = "amount_money", nullable= false)
    private BigDecimal amountMoney;

    @Digits(integer = 12, fraction = 2)
    @Column(name = "discount_amount", nullable= false)
    private BigDecimal discountAmount;

    @Digits(integer = 12, fraction = 2)
    @Column(name = "total_amount", nullable= false)
    private BigDecimal totalAmount;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private Order order;

    @Column(columnDefinition = "boolean default false")
    private boolean paid;

   public BillDTO billDTO(){
       BillDTO billDTO = new BillDTO() ;
       billDTO.setId(id) ;
       billDTO.setCreateAt(getCreatedAt().toString());
       String date = AppUtils.dateFormat(getCreatedAt().toString()) ;
       billDTO.setDate(date) ;
       String time = AppUtils.timeFormat(getCreatedAt().toString()) ;
       billDTO.setTime(time) ;
       billDTO.setCustomerName(order.getCustomer().getName()) ;
       if(voucher!=null){
           billDTO.setVoucherName(voucher.getName()) ;
       }else {
           billDTO.setVoucherName("Null") ;
       }
       billDTO.setTotalAmount(totalAmount) ;
       return billDTO ;
   }

}
