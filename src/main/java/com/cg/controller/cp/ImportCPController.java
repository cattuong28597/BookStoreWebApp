package com.cg.controller.cp;

import com.cg.model.Import;
import com.cg.model.Product;
import com.cg.service.export.ExportService;
import com.cg.service.importT.ImportService;
import com.cg.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/cp/imports")
public class ImportCPController {
    @Autowired
    private ImportService importService;

    @Autowired
    private ExportService exportService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ModelAndView showCpImportIndex() {
        List<Import> imports = importService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("imports", imports);
        modelAndView.setViewName("cp/importT/list");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCpImportCreate() {

        ModelAndView modelAndView = new ModelAndView();
        List<Product> products = productService.findAll();
        modelAndView.addObject("products", products);
        modelAndView.addObject("import", new Import());

        modelAndView.setViewName("cp/importT/create");

        return modelAndView;
    }

    @PostMapping(value = "/create")
    public ModelAndView create(@Validated @ModelAttribute("import") Import importT, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        List<Product> products = productService.findAll();
        modelAndView.addObject("products", products);

        if (bindingResult.hasFieldErrors()) {
            modelAndView.addObject("script", true);
            return modelAndView;
        }else {
            try {
                importService.save(importT);
                Product product = importT.getProductImport();
                int quan = importService.sumQuatityWithIdProduct(product.getId());
                int quanE = exportService.sumQuatityWithIdProduct(product.getId());
                int sumQ = quan - quanE;
                product.setQuantity(sumQ);

                productService.save(product);

                modelAndView.addObject("success", "Successfully added new product");
            } catch (Exception e) {
                if (!(bindingResult.hasFieldErrors())) {
                    e.printStackTrace();
                    modelAndView.addObject("import", new Import());
                    modelAndView.addObject("error", "Invalid data, please contact system administrator");
                }
            }
        }

        modelAndView.addObject("import", new Import());
        modelAndView.setViewName("cp/importT/create");
        return modelAndView;
    }
}
