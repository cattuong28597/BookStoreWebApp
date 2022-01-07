package com.cg.controller.cp;

import com.cg.model.Product;
import com.cg.model.Role;
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
@RequestMapping("cp/users")
public class UserCPController {

    @Autowired
    private IUserService userService ;

    @Autowired
    private IRoleService roleService;

    @GetMapping("")
    public ModelAndView showAllUser(){
        ModelAndView modelAndView = new ModelAndView("cp/user/list") ;
        List<User> userList = userService.findAllByDeletedIsFalse() ;
        modelAndView.addObject("userList",userList);
        return modelAndView;
    }
    @GetMapping("/create")
    public ModelAndView createUser(){
        ModelAndView modelAndView = new ModelAndView("cp/user/create") ;
        List<Role> roleList = roleService.findAll();
        modelAndView.addObject("roleList",roleList);
        modelAndView.addObject("user", new User());

        return modelAndView ;
    }

}
