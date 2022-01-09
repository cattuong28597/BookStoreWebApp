package com.cg.controller.cp;


import com.cg.model.Category;
import com.cg.model.CategoryGroup;
import com.cg.model.Product;
import com.cg.service.categorygroup.CategoryGroupService;
import com.cg.service.product.ProductService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cp/products")
public class ProductCPController {

    @Autowired
    private CategoryGroupService categoryGroupService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ModelAndView showCpProductIndex() {
        List<Product> products = productService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", products);
        modelAndView.setViewName("cp/product/list1");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCpProductCreate() {

        ModelAndView modelAndView = new ModelAndView();

        List<CategoryGroup> categoryGroups = categoryGroupService.findAll();

        modelAndView.addObject("categoryGroups", categoryGroups);
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

    @PostMapping(value = "/create")
    public ModelAndView create(@Validated @ModelAttribute("product") Product product, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        List<CategoryGroup> categoryGroups = categoryGroupService.findAll();

        modelAndView.addObject("categoryGroups", categoryGroups);

        if (bindingResult.hasFieldErrors()) {
            modelAndView.addObject("script", true);
            modelAndView.addObject("error1", "Invalid data, please contact system administrator");
        } else {

            String slug = "A";

            try {
                product.setSlug(slug);
                productService.save(product);

                Optional<Product> productCheck = productService.findBySlugAndIdIsNot(slug, 0L);

                Product product1 = productCheck.get();
                String slugTemp = product1.getName() + "-" + product1.getId();
                String slugTemp1 = AppUtils.removeNonAlphanumeric(slugTemp);
                product1.setSlug(slugTemp1);
                productService.save(product1);

                modelAndView.addObject("product", new Product());
                modelAndView.addObject("success", "Successfully added new product");
            } catch (Exception e) {
                e.printStackTrace();
                modelAndView.addObject("error", "Invalid data, please contact system administrator");
            }
        }

        modelAndView.setViewName("cp/product/create");

        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEdit(@PathVariable Long id) {

        ModelAndView modelAndView = new ModelAndView();

        Optional<Product> product = productService.findById(id);

        if (product.isPresent()) {
            modelAndView.addObject("product", product);
        } else {
            modelAndView.addObject("product", new Product());
            modelAndView.addObject("script", false);
            modelAndView.addObject("success", false);
            modelAndView.addObject("error", "Invalid product information");
        }

        modelAndView.setViewName("cp/product/edit");

        return modelAndView;
    }

    @PostMapping(value = "/edit/{id}")
    public ModelAndView update(@PathVariable long id, @Validated @ModelAttribute("product") Product product, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        List<CategoryGroup> categoryGroups = categoryGroupService.findAll();

        modelAndView.addObject("categoryGroups", categoryGroups);

        if (bindingResult.hasFieldErrors()) {
            modelAndView.addObject("script", true);
        } else {

            String slugTemp = product.getName() + "-" + product.getId();
            String slugTemp1 = AppUtils.removeNonAlphanumeric(slugTemp);

            try {
                product.setSlug(slugTemp1);
                productService.save(product);

                modelAndView.addObject("success", "Product has been successfully updated");
            } catch (Exception e) {
                e.printStackTrace();
                modelAndView.addObject("error", "Invalid data, please contact system administrator");
            }

        }

        modelAndView.setViewName("cp/product/edit");

        return modelAndView;
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable long id, @Validated @ModelAttribute("product") Product product, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        Optional<Product> productCheck = productService.findById(id);

        if(productCheck.isPresent()){
            try {

                Product product1 = productCheck.get();

                product1.setDeleted(true);
                productService.save(product1);
                List<Product> products = productService.findAll();
                modelAndView.addObject("products", products);

                modelAndView.addObject("success", "Product has been successfully deleted");
            } catch (Exception e) {
                e.printStackTrace();
                List<Product> products = productService.findAll();
                modelAndView.addObject("products", products);
                modelAndView.addObject("error", "Invalid data, please contact system administrator");
            }
        }else {
            List<Product> products = productService.findAll();
            modelAndView.addObject("products", products);
            modelAndView.addObject("error", "Product does not exist");
        }

        modelAndView.setViewName("cp/product/list1");

        return modelAndView;
    }

}
