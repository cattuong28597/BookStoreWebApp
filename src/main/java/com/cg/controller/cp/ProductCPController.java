package com.cg.controller.cp;


import com.cg.model.Category;
import com.cg.model.Product;
import com.cg.service.category.CategoryService;
import com.cg.service.product.ProductService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cp/products")
public class ProductCPController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ModelAndView showCpProductIndex() {
        List<Product> products = productService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", products);
        modelAndView.setViewName("cp/product/list");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCpProductCreate() {

        ModelAndView modelAndView = new ModelAndView();

        List<Category> categories = categoryService.findAll();

        modelAndView.addObject("categories", categories);
        modelAndView.addObject("product", new Product());

        modelAndView.setViewName("cp/product/create");

        return modelAndView;
    }

    @GetMapping("/upload")
    public ModelAndView showCpProductUpload() {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("cp/product/upload");

        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEdit(@PathVariable Long id) {

        ModelAndView modelAndView = new ModelAndView();

        Optional<Product> product = productService.findById(id);

        if (product.isPresent()) {
            modelAndView.setViewName("cp/product/edit");
            modelAndView.addObject("product", product);
        } else {
            modelAndView.addObject("errorType", "404");
            modelAndView.addObject("errorMsg", "Http Error Code: 404. Resource not found");
            modelAndView.setViewName("errorPage");
        }
        return modelAndView;
    }

}
