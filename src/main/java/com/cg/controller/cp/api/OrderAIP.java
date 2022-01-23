package com.cg.controller.cp.api;

import com.cg.exception.DataInputException;
import com.cg.model.*;
import com.cg.model.dto.BillDTO;
import com.cg.model.dto.OrderDTO;
import com.cg.service.bill.BillService;
import com.cg.service.cart.CartService;
import com.cg.service.cart_details.CartDetailService;
import com.cg.service.customer.CustomerService;
import com.cg.service.order.OrderService;
import com.cg.service.order_detail.OrderDetailService;
import com.cg.service.voucher.VoucherService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cp/api/order")
public class OrderAIP {

    @Autowired
    private CartService cartService;

    @Autowired
    private BillService billService;

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CartDetailService cartDetailsService;

    @Autowired
    private AppUtils appUtils;

    @PostMapping("/create")
    public ResponseEntity<?> creatOrder(@RequestBody OrderDTO orderDTO) {
        Optional<Cart> cartOptional = cartService.findById(orderDTO.getCartId());
        Optional<Customer> customerOptional = customerService.findById(orderDTO.getCustomerId());
        if (!cartOptional.isPresent()) {
            throw new DataInputException("Cart not exits !");
        }
        if (!customerOptional.isPresent()) {
            throw new DataInputException("Customer not exits !");
        }

        Cart cart = cartOptional.get();

        Customer customer = customerOptional.get();

        if (cart.getCustomer().getId() != customer.getId()) {
            throw new DataInputException("Data not true !");
        }

        try {
            List<CartDetail> cartDetailList = cartDetailsService.findCartDetailByCartAndDeletedIsFalse(cart);
            Order order = new Order();
            order.setCustomer(customer);
            orderService.save(order);

            BigDecimal totalAmount = BigDecimal.valueOf(0);
            for (CartDetail cartDetail : cartDetailList) {
                OrderDetail orderDetail = cartDetail.toOrderDetail();
                orderDetail.setOrder(order);
                orderDetailService.save(orderDetail);
                totalAmount = totalAmount.add(orderDetail.getTotal());
                cartDetail.setDeleted(true);
                cartDetailsService.save(cartDetail);
            }
            order.setTotalAmount(totalAmount);
            orderService.save(order);


            return new ResponseEntity<>(order, HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Order creation information is not valid, please check the information again");
        }
    }


    @PostMapping("/confirm")
    public ResponseEntity<?> confirmOrder(@RequestBody BillDTO billDTO) {
        Optional<Order> orderOptional = orderService.findById(billDTO.getOrderId());
        if (!orderOptional.isPresent()) {
            throw new DataInputException("Not exist order ! Please check again !");
        }
        Order order = orderOptional.get();
        if (order.isDeleted()) {
            throw new DataInputException("Order paid ! ");
        }
        Bill bill = new Bill();
        bill.setAmountMoney(order.getTotalAmount());
        bill.setOrder(order);
        BigDecimal discountAmount = BigDecimal.valueOf(0);
        Optional<Voucher> voucherOptional = voucherService.findById(billDTO.getVoucherId());

        if (voucherOptional.isPresent()) {
            Voucher voucher = voucherOptional.get();
            if (voucher.isDeleted()) {
                throw new DataInputException("The voucher has expired !");
            } else {
                BigDecimal per = BigDecimal.valueOf(voucher.getPercentageDiscount()).divide(BigDecimal.valueOf(100));
                discountAmount = order.getTotalAmount().multiply(per);
                discountAmount = discountAmount.setScale(2, RoundingMode.CEILING) ;

                bill.setVoucher(voucher);
            }
        }

        bill.setDiscountAmount(discountAmount);
        bill.setTotalAmount(bill.getAmountMoney().subtract(bill.getDiscountAmount()));

        try {
            billService.save(bill);
            order.setConfirm(true);
            orderService.save(order);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Load fail , please check again !");
        }
    }
}
