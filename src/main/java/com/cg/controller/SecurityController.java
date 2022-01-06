package com.cg.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SecurityController {

    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    @GetMapping(value = "/log")
    public String Homepage(Model model) {
        model.addAttribute("user", getPrincipal());
        return "/login";
    }

    @GetMapping( "/login")
    public ModelAndView getLogin() {
        return new ModelAndView("/login");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminPage(ModelMap modelMap) {
        modelMap.addAttribute("user", getPrincipal());
        return "/cp/categories";
    }

//    @GetMapping("/admin")
//    @PreAuthorize("hasAuthority('DBA')")
//    public String dbaPage(ModelMap modelMap) {
//        modelMap.addAttribute("user", getPrincipal());
//        return "/cp/categories";
//    }

//    @GetMapping( "/dba")
//    public String dbaPage(ModelMap model) {
//        model.addAttribute("user", getPrincipal());
//        return "/cp";
//    }

    @GetMapping("/accessDenied")
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "/403";
    }

    @GetMapping("/notFound")
    public String notFound(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "/404";
    }

}
