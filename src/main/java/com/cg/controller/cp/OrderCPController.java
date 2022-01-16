package com.cg.controller.cp;

import com.cg.model.Order;
import com.cg.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/cp/orders")
public class OrderCPController {
    @Autowired
    private OrderService orderService ;

    @GetMapping()
    public ModelAndView showAllOrder(){
        ModelAndView modelAndView = new ModelAndView("cp/order/list") ;
        List<Order> orderList = orderService.findAllByDeletedIsFalse();
        modelAndView.addObject(orderList) ;
        return modelAndView ;
    }
}
