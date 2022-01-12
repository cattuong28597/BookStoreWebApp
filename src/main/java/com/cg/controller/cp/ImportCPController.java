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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

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
        } else {

            try {
                importService.save(importT);
                Product product = productService.findById(importT.getProductImport().getId()).get();
                int quan =importService.sumQuatityWithIdProduct(product.getId());
                int quanE =exportService.sumQuatityWithIdProduct(product.getId());
                product.setQuantity(quan - quanE);
                productService.save(product);

                modelAndView.addObject("import", new Import());
                modelAndView.addObject("success", "Successfully added new product");
            } catch (Exception e) {
                if (!(bindingResult.hasFieldErrors())) {
                    e.printStackTrace();
                    modelAndView.addObject("import", new Import());
                    modelAndView.addObject("error", "Invalid data, please contact system administrator");
                }
            }
        }

        modelAndView.setViewName("cp/importT/create");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEdit(@PathVariable Long id) {

        ModelAndView modelAndView = new ModelAndView();
        Optional<Import> importOptional = importService.findById(id);

        if (importOptional.isPresent()) {

            if (importOptional.get().isDeleted()) {
                List<Import> imports = importService.findAll();
                modelAndView.addObject("imports", imports);
                modelAndView.addObject("error", "Deleted product");
                modelAndView.setViewName("cp/importT/list");
            } else {
                modelAndView.addObject("import", importOptional.get());
                List<Product> products = productService.findAll();
                modelAndView.addObject("products", products);
                modelAndView.setViewName("cp/importT/edit");
            }
        } else {
            List<Import> imports = importService.findAll();
            modelAndView.addObject("imports", imports);
            modelAndView.setViewName("cp/importT/list");
        }

        return modelAndView;
    }

    @PostMapping(value = "/edit/{id}")
    public ModelAndView update(@Validated @ModelAttribute("import") Import importT, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        List<Product> products = productService.findAll();
        modelAndView.addObject("products", products);

        Boolean deleted = importService.findById(importT.getId()).get().isDeleted();



        if (bindingResult.hasFieldErrors()) {
            modelAndView.addObject("script", true);
        } else {
            try {
                importT.setDeleted(deleted);
                importService.save(importT);
                Product product = productService.findById(importT.getProductImport().getId()).get();
                product.setQuantity(importService.sumQuatityWithIdProduct(importT.getId()));

                modelAndView.addObject("success", "Import has been successfully updated");
            } catch (Exception e) {
                if (!(bindingResult.hasFieldErrors())) {
                    e.printStackTrace();
                    modelAndView.addObject("error", "Invalid data, please contact system administrator");
                }
            }
        }

        modelAndView.setViewName("cp/importT/edit");

        return modelAndView;
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView showDelete(@PathVariable long id) {

        ModelAndView modelAndView = new ModelAndView();

        Optional<Import> importOptional = importService.findById(id);

        if (importOptional.isPresent()) {
            if (importOptional.get().isDeleted()) {
                List<Import> imports = importService.findAll();
                modelAndView.addObject("imports", imports);
                modelAndView.addObject("error", "Deleted product");
                modelAndView.setViewName("cp/importT/list");
            } else {
                modelAndView.addObject("import", importOptional.get());
                List<Product> products = productService.findAll();
                modelAndView.addObject("products", products);
                modelAndView.setViewName("cp/importT/delete");
            }
        } else {
            List<Import> imports = importService.findAll();
            modelAndView.addObject("imports", imports);
            modelAndView.setViewName("cp/importT/list");
        }

        return modelAndView;
    }

    @PostMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable long id) {

        ModelAndView modelAndView = new ModelAndView();
        List<Product> products = productService.findAll();
        modelAndView.addObject("products", products);
        Import anImport = importService.findById(id).get();

        try {
            if (anImport.isDeleted()){
                modelAndView.addObject("import", anImport);
                modelAndView.addObject("error", "Deleted import");
            }else {
                anImport.setDeleted(true);
                importService.save(anImport);
                modelAndView.addObject("import", anImport);
                modelAndView.addObject("success", "Import has been successfully deleted");
            }
        } catch (Exception e) {
            modelAndView.addObject("import", anImport);
            modelAndView.addObject("error", "Invalid data, please contact system administrator");
        }

        modelAndView.setViewName("cp/importT/delete");
        return modelAndView;
    }
}
