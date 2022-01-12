package com.cg.controller.cp;

import com.cg.model.CartDetail;
import com.cg.model.Product;
import com.cg.service.cart.CartService;
import com.cg.service.cartdetail.CartDetailService;
import com.cg.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("cp/cart-detail")
public class CartDetailCPController {
    @Autowired
    private CartDetailService cartDetailService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ModelAndView showCpCartDetailsIndex() {
        List<CartDetail> cartDetails = cartDetailService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("cartDetails", cartDetails);
        modelAndView.setViewName("cp/cart-detail/list");
        return modelAndView;
    }

    @GetMapping("/cart/{id}")
    public ModelAndView showCpCartDetailsIndex(@PathVariable Long id) {
        List<CartDetail> cartDetails = cartDetailService.findCartDetailByCartAndDeletedIsFalse(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("cartDetails", cartDetails);
        modelAndView.setViewName("cart");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEdit(@PathVariable Long id) {

        ModelAndView modelAndView = new ModelAndView();

        Optional<CartDetail> cartDetail = cartDetailService.findById(id);

        if (cartDetail.isPresent()) {
            CartDetail cartDetail1 = cartDetail.get();
            Product product = productService.getById(cartDetail1.getId());
            modelAndView.addObject("product", product);
            modelAndView.addObject("cart-detail", cartDetail1);
            modelAndView.setViewName("cart-detail-edit");
            return modelAndView;
        } else {
            modelAndView.addObject("product", new Product());
            modelAndView.addObject("cart-detail", new CartDetail());
            modelAndView.setViewName("cart-detail-edit");
            return modelAndView;
        }
    }

    @PostMapping(value = "/edit/{id}")
    public ModelAndView update(@Validated @ModelAttribute("cart-detail") CartDetail cartDetail, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasFieldErrors()) {
            modelAndView.addObject("script", true);
        } else {

            try {
                cartDetailService.save(cartDetail);
                modelAndView.addObject("success", "Cart detail has been successfully updated");
            } catch (Exception e) {
                if (!(bindingResult.hasFieldErrors())){
                    e.printStackTrace();
                    modelAndView.addObject("error", "Invalid data, please contact system administrator");
                }
            }
        }

        modelAndView.setViewName("cart-detail-edit");

        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDelete(@PathVariable Long id) {

        ModelAndView modelAndView = new ModelAndView();

        Optional<CartDetail> cartDetail = cartDetailService.findById(id);

        if (cartDetail.isPresent()) {
            CartDetail cartDetail1 = cartDetail.get();
            Product product = productService.getById(cartDetail1.getId());
            modelAndView.addObject("product", product);
            modelAndView.addObject("cart-detail", cartDetail1);
            modelAndView.setViewName("cart-detail-edit");
            return modelAndView;
        } else {
            modelAndView.addObject("product", new Product());
            modelAndView.addObject("cart-detail", new CartDetail());
            modelAndView.setViewName("cart-detail-edit");
            return modelAndView;
        }
    }

    @PostMapping(value = "/delete/{id}")
    public ModelAndView delete(@Validated @ModelAttribute("cart-detail") CartDetail cartDetail, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasFieldErrors()) {
            modelAndView.addObject("script", true);
        } else {

            try {
                if (cartDetail.isDeleted()){
                    modelAndView.addObject("error", "Deleted cart detail");
                }else {
                    cartDetail.setDeleted(true);
                    cartDetailService.save(cartDetail);
                    modelAndView.addObject("success", "Cart detail has been successfully deleted");
                }

            } catch (Exception e) {
                if (!(bindingResult.hasFieldErrors())){
                    e.printStackTrace();
                    modelAndView.addObject("error", "Invalid data, please contact system administrator");
                }
            }
        }

        modelAndView.setViewName("cart-detail-delete");

        return modelAndView;
    }
}