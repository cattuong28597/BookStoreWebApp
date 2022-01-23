package com.cg.controller.cp;

import com.cg.model.Bill;
import com.cg.model.Order;
import com.cg.model.OrderDetail;
import com.cg.model.Voucher;
import com.cg.model.dto.BillDTO;
import com.cg.service.bill.BillService;
import com.cg.service.order_detail.OrderDetailService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cp/bills")
public class BillCPController {

    @Autowired
    private BillService billService;

    @Autowired
    private OrderDetailService orderDetailService;

  @GetMapping("")
    public ModelAndView showAllBill(){
      ModelAndView modelAndView = new ModelAndView("cp/bill/list");
      List<Bill> billList = billService.findAllByDeletedIsFalse() ;
      modelAndView.addObject("billList",billList) ;
      return modelAndView;

  }

    @GetMapping("/details/{id}")
    public ModelAndView showDetails(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("cp/bill/details");
        Optional<Bill> billOptional = billService.findById(id);
        if (!billOptional.isPresent()) {
            modelAndView.setViewName("errorPage");
            return modelAndView;
        } else if (billOptional.get().isDeleted()) {
            modelAndView.setViewName("errorPage");
            return modelAndView;
        }
        Bill bill = billOptional.get();
        Order order = bill.getOrder();

        List<OrderDetail> orderDetailList = orderDetailService.findAllByOrder(order);

        modelAndView.addObject("order", order);
        modelAndView.addObject("orderDetailList", orderDetailList);
        modelAndView.addObject("bill",bill);
        return modelAndView;
    }

   @GetMapping("/statistical")
    public ModelAndView statisticalBills(){
      ModelAndView modelAndView = new ModelAndView("cp/statistical/list");
//       Date startDate = AppUtils.convertoString("2022-1-10") ;
//       Date endDate = AppUtils.convertoString("2022-1-20 23:59:59") ;
//       String startDateStr = new Date().toString() ;
//       startDateStr = startDateStr.substring(0,10) ;

 //tìm kiếm bill thanh toán trong 1 ngày
       long timeadj = 24*60*60*1000;
       Date endDate = new Date();
       Date startDate = new Date(endDate.getTime() - timeadj) ;


      List<Bill> billList = billService.findAllByDeletedIsFalseAndPaidIsTrueAndCreatedAtIsBetween(startDate,endDate) ;
       List<BillDTO> billDTOList = new ArrayList<>() ;
      BigDecimal totalMoney = BigDecimal.valueOf(0) ;
      for(Bill bill : billList){
       String dateCr =   bill.getCreatedAt().toString();
          totalMoney = totalMoney.add(bill.getTotalAmount()) ;
          billDTOList.add(bill.billDTO()) ;
      }
       modelAndView.addObject("totalMoney",totalMoney) ;
       modelAndView.addObject("billDTOList",billDTOList) ;
      return modelAndView ;
   }

}
