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
        modelAndView.setViewName("cp/product/list");
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
                if (!(bindingResult.hasFieldErrors())){
                    e.printStackTrace();
                    modelAndView.addObject("error", "Invalid data, please contact system administrator");
                }
            }
        }

        modelAndView.setViewName("cp/product/create");

        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEdit(@PathVariable Long id) {

        ModelAndView modelAndView = new ModelAndView();

        Optional<Product> product = productService.findById(id);
        List<CategoryGroup> categoryGroups = categoryGroupService.findAll();

        if (product.isPresent()) {
            Product product1 = product.get();
            if (product1.isDeleted()){
                List<Product> products = productService.findAll();
                modelAndView.addObject("products", products);
                modelAndView.addObject("error", "Deleted product");
                modelAndView.setViewName("cp/product/list");
                return modelAndView;
            }else {
                modelAndView.addObject("product", product);
                modelAndView.addObject("categoryGroups", categoryGroups);
                modelAndView.setViewName("cp/product/edit");
                return modelAndView;
            }
        } else {
            List<Product> products = productService.findAll();
            modelAndView.addObject("products", products);
            modelAndView.setViewName("cp/product/list");
            return modelAndView;
        }
    }

    @PostMapping(value = "/edit/{id}")
    public ModelAndView update(@Validated @ModelAttribute("product") Product product, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        List<CategoryGroup> categoryGroups = categoryGroupService.findAll();
        modelAndView.addObject("categoryGroups", categoryGroups);
        Boolean deleted = productService.findById(product.getId()).get().isDeleted();

        if (bindingResult.hasFieldErrors()) {
            modelAndView.addObject("script", true);
        } else {

            String slugTemp = product.getName() + "-" + product.getId();
            String slugTemp1 = AppUtils.removeNonAlphanumeric(slugTemp);

            try {
                product.setSlug(slugTemp1);
                product.setDeleted(deleted);
                productService.save(product);

                modelAndView.addObject("success", "Product has been successfully updated");
            } catch (Exception e) {
                if (!(bindingResult.hasFieldErrors())){
                    e.printStackTrace();
                    modelAndView.addObject("error", "Invalid data, please contact system administrator");
                }
            }
        }

        modelAndView.setViewName("cp/product/edit");

        return modelAndView;
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView showDelete(@PathVariable long id) {

        ModelAndView modelAndView = new ModelAndView();

        Optional<Product> product = productService.findById(id);
        List<CategoryGroup> categoryGroups = categoryGroupService.findAll();

        if (product.isPresent()) {
            if (product.get().isDeleted()){
                List<Product> products = productService.findAll();
                modelAndView.addObject("products", products);
                modelAndView.addObject("error", "Deleted product");
                modelAndView.setViewName("cp/product/list");
                return modelAndView;
            }else {
                modelAndView.addObject("product", product);
                modelAndView.addObject("categoryGroups", categoryGroups);
                modelAndView.setViewName("cp/product/delete");
                return modelAndView;
            }
        } else {
            modelAndView.setViewName("cp/product/list");
            List<Product> products = productService.findAll();
            modelAndView.addObject("products", products);
            return modelAndView;
        }
    }

    @PostMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable long id) {

        ModelAndView modelAndView = new ModelAndView();

        List<CategoryGroup> categoryGroups = categoryGroupService.findAll();

        modelAndView.addObject("categoryGroups", categoryGroups);

        Product product = productService.findById(id).get();
        modelAndView.addObject("product", product);

            try {
                if (product.isDeleted()){
                    modelAndView.addObject("error", "Deleted product");
                }else {
                    product.setDeleted(true);
                    productService.save(product);
                    modelAndView.addObject("success", "Product has been successfully deleted");
                }

            } catch (Exception e) {
                    e.printStackTrace();
                    modelAndView.addObject("error", "Invalid data, please contact system administrator");
            }

        modelAndView.setViewName("cp/product/delete");

        return modelAndView;
    }
}
