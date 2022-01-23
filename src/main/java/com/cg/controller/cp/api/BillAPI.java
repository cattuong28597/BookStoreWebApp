package com.cg.controller.cp.api;

import com.cg.exception.DataInputException;
import com.cg.model.Bill;
import com.cg.model.Order;
import com.cg.model.dto.BillDTO;
import com.cg.model.dto.IBillDTO;
import com.cg.model.dto.StatisticalDTO;
import com.cg.service.bill.BillService;
import com.cg.service.order.OrderService;
import com.cg.utils.AppUtils;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("/cp/api/bill")
public class BillAPI {

    @Autowired
    private BillService billService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AppUtils appUtils;

    @GetMapping("/confirm/{id}")
    public ResponseEntity<?> confirmBill(@PathVariable Long id) {
        Optional<Bill> billOptional = billService.findById(id);
        if (!billOptional.isPresent()) {
            throw new DataInputException("Bill not exist ! Please check data again !");
        }
        Bill bill = billOptional.get();
        if (bill.isDeleted()) {
            throw new DataInputException("Invoice has been issued ! ");
        }
        try {
            Order order = bill.getOrder() ;
            order.setDeleted(true);
            bill.setPaid(true);
            billService.save(bill) ;
            orderService.save(order);
           return  new ResponseEntity<>(HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Load fail , please check again !");
        }
    }

    @PostMapping("/statistical")
    public ResponseEntity<?> statisticalBill(@Validated @RequestBody StatisticalDTO statisticalDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Date startDate = AppUtils.convertoString(statisticalDTO.getStartDate());
        Date endDate = AppUtils.convertoString(statisticalDTO.getEndDate()) ;

        if(startDate.compareTo(endDate) > 0){
            throw new DataInputException("The end date must be greater than the start date !");
        }
        long timeadj = 24*60*60*1000;
        endDate = new Date (endDate.getTime ()+timeadj);
        List<Bill> billList = billService.findAllByDeletedIsFalseAndPaidIsTrueAndCreatedAtIsBetween(startDate,endDate) ;
        List<BillDTO> billDTOS = new ArrayList<>() ;
        for (Bill bill : billList){
            BillDTO billDTO = bill.billDTO() ;
            billDTOS.add(billDTO) ;
        }
        return new ResponseEntity<>(billDTOS, HttpStatus.OK);
    }
}
