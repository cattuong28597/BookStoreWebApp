package com.cg.controller.cp;


import com.cg.model.User;
import com.cg.service.role.IRoleService;
import com.cg.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/cp")
public class ControlPanelController {


    @GetMapping("")
    public ModelAndView showCPHomePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cp/temp");
        return modelAndView;
    }

//    @GetMapping("/users")
//    public ModelAndView showCpUserIndex() {
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("cp/user/list");
//        List<User> userList = userService.findAllByDeletedIsFalse() ;
//        modelAndView.addObject("userList",userList);
//        return modelAndView;
//    }
//
//    @GetMapping("/users/create")
//    public ModelAndView showCpUserCreate() {
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("cp/user/create");
//
//        return modelAndView;
//    }
}
