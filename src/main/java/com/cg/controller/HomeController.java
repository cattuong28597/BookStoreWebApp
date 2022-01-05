package com.cg.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class HomeController {

    @GetMapping
    public ModelAndView showHomePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        String title = "Pustok - Book Store HTML Template";
        modelAndView.addObject("title", title);
        return modelAndView;
    }

    @GetMapping("/login-register.html")
    public ModelAndView showLoginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/login-register");
        String title = "Pustok - Book Store HTML Template";
        modelAndView.addObject("title", title);
        return modelAndView;
    }

    @GetMapping("/products/details")
    public ModelAndView showProductDetailPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/product-detail");
        String title = "Pustok - Book Store HTML Template";
        modelAndView.addObject("title", title);
        return modelAndView;
    }

    @GetMapping("/my-account.html")
    public ModelAndView listCustomers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/my-account");
        String title = "Pustok - Book Store HTML Template";
        modelAndView.addObject("title", title);
        return modelAndView;
    }

    @GetMapping("/cart.html")
    public ModelAndView showCartPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/cart");
        String title = "Pustok - Book Store HTML Template";
        modelAndView.addObject("title", title);
        return modelAndView;
    }

    @GetMapping("/order-complete.html")
    public ModelAndView showOrderCompletePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/order-complete");
        String title = "Pustok - Book Store HTML Template";
        modelAndView.addObject("title", title);
        return modelAndView;
    }

    @GetMapping("/checkout.html")
    public ModelAndView showCheckoutPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("checkout");
        String title = "Pustok - Book Store HTML Template";
        modelAndView.addObject("title", title);
        return modelAndView;
    }
}
