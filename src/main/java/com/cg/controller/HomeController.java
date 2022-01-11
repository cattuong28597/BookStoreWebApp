package com.cg.controller;


import com.cg.model.Category;
import com.cg.model.CategoryGroup;
import com.cg.model.Product;
import com.cg.service.category.CategoryService;
import com.cg.service.categorygroup.CategoryGroupService;
import com.cg.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryGroupService categoryGroupService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ModelAndView showHomePage() {

        List<CategoryGroup> categoryGroups = categoryGroupService.findAll();

        List<Category> categories = categoryService.findAll();

        List<Product> productList = productService.findAll();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        String title = "Sunrise Book-Store";
        modelAndView.addObject("title", title);

        modelAndView.addObject("categoryGroups", categoryGroups);
        modelAndView.addObject("categories", categories);

        modelAndView.addObject("BestSellers", productList);
//        12 quyen sach duoc confirm order nhiu nhat
        modelAndView.addObject("SpecialOffer", productList);
//        6 quyen sach co rate sao cao nhat
        modelAndView.addObject("FeaturedProducts", productList);
//        12 quyen sach moi tao gan day nhat voi so luong = 0
        modelAndView.addObject("NewArrivals", productList);
//        12 quyen sach moi duoc nhap hang gan day nhat
        modelAndView.addObject("MostChoice", productList);
//        12 quyen sach duoc order nhieu nhat
        modelAndView.addObject("Category1", productList);
//        12 quyen sach trong loai sach duoc chon
        modelAndView.addObject("Category2", productList);
//        12 quyen sach trong loai sach duoc chon
        modelAndView.addObject("Category3", productList);
//        12 quyen sach trong loai sach duoc chon

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

    @GetMapping("/products/{slug}")
    public ModelAndView showProductDetailPage(@PathVariable String slug) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/product-detail");
//        Product product = productService.existsBySlugEquals(slug);
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

    @GetMapping("/demo")
    public ModelAndView showDemo(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/cp/demo");
        return modelAndView;
    }

    @GetMapping("/testErrorPage")
    public ModelAndView showErrorPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errorPage");
        modelAndView.addObject("errorType", "401");
        modelAndView.addObject("errorMsg", "Http Error Code: 404. Resource not found");
        return modelAndView;
    }
}
