package com.cg.controller.cp;

import com.cg.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cart_details")
public class CartDetailController {
    @GetMapping
    public ModelAndView getList() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/cart1");
        return modelAndView;
    }
}
