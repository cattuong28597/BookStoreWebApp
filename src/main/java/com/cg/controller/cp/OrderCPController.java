package com.cg.controller.cp;

import com.cg.model.Order;
import com.cg.model.OrderDetail;
import com.cg.model.Voucher;
import com.cg.repository.OrderDetailRepository;
import com.cg.service.order.OrderService;
import com.cg.service.voucher.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cp/orders")
public class OrderCPController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private VoucherService voucherService ;

    @GetMapping()
    public ModelAndView showAllOrder() {
        ModelAndView modelAndView = new ModelAndView("cp/order/list");
        List<Order> orderList = orderService.findAllByDeletedIsFalse();

        modelAndView.addObject("orderList",orderList);
        return modelAndView;
    }

    @GetMapping("/details/{id}")
    public ModelAndView showDetails(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("cp/order/details");
        Optional<Order> orderOptional = orderService.findById(id);
        if (!orderOptional.isPresent()) {
            modelAndView.setViewName("errorPage");
            return modelAndView;
        } else if (orderOptional.get().isDeleted()) {
            modelAndView.setViewName("errorPage");
            return modelAndView;
        }
        Order order = orderOptional.get();
        List<Voucher> vouchersList = voucherService.findVoucherByDeletedIsFalse();
        List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrder(order);

        modelAndView.addObject("order", order);
        modelAndView.addObject("vouchersList", vouchersList);
        modelAndView.addObject("orderDetailList", orderDetailList);
        return modelAndView;
    }
}
