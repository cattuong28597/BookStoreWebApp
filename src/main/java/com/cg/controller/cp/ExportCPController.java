package com.cg.controller.cp;

import com.cg.model.Export;
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
@RequestMapping("/cp/exports")
public class ExportCPController {
    @Autowired
    private ImportService importService;

    @Autowired
    private ExportService exportService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ModelAndView showCpExportIndex() {
        List<Export> exports = exportService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exports", exports);
        modelAndView.setViewName("cp/export/list");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCpExportCreate() {

        ModelAndView modelAndView = new ModelAndView();

        List<Product> products = productService.findAll();

        modelAndView.addObject("products", products);
        modelAndView.addObject("export", new Export());

        modelAndView.setViewName("cp/export/create");

        return modelAndView;
    }

    @PostMapping(value = "/create")
    public ModelAndView create(@Validated @ModelAttribute("export") Export export,  BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        List<Product> products = productService.findAll();
        modelAndView.addObject("products", products);

        if (bindingResult.hasFieldErrors()) {
            modelAndView.addObject("script", true);
        } else {

            try {
                Product product = productService.findById(export.getProductExport().getId()).get();
                if(product.getQuantity() >= export.getQuantity()){
                    int quan = importService.sumQuatityWithIdProduct(product.getId());
                    int quanE = exportService.sumQuatityWithIdProduct(product.getId());
                    product.setQuantity(quan - quanE);
                    exportService.save(export);
                    productService.save(product);

                    modelAndView.addObject("success", "Export has been successfully updated");
                }else {
                    modelAndView.addObject("error", "The number of products is not enough");
                }
            } catch (Exception e) {
                if (!(bindingResult.hasFieldErrors())) {
                    e.printStackTrace();

                    modelAndView.addObject("error", "Invalid data, please contact system administrator");
                }
            }
        }
        modelAndView.addObject("export", new Export());
        modelAndView.setViewName("cp/export/create");

        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEdit(@PathVariable Long id) {

        ModelAndView modelAndView = new ModelAndView();
        Optional<Export> exportOptional = exportService.findById(id);

        if (exportOptional.isPresent()) {

            if (exportOptional.get().isDeleted()) {
                List<Export> exports = exportService.findAll();
                modelAndView.addObject("exports", exports);
                modelAndView.addObject("error", "Deleted product");
                modelAndView.setViewName("cp/export/list");
            } else {
                modelAndView.addObject("export", exportOptional.get());
                List<Product> products = productService.findAll();
                modelAndView.addObject("products", products);
                modelAndView.setViewName("cp/export/edit");
            }
        } else {
            List<Export> exports = exportService.findAll();
            modelAndView.addObject("exports", exports);
            modelAndView.setViewName("cp/export/list");
        }

        return modelAndView;
    }

    @PostMapping(value = "/edit/{id}")
    public ModelAndView update(@Validated @ModelAttribute("export") Export exportT, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        List<Product> products = productService.findAll();
        modelAndView.addObject("products", products);
        Boolean deleted = exportService.findById(exportT.getId()).get().isDeleted();

        if (bindingResult.hasFieldErrors()) {
            modelAndView.addObject("script", true);
        } else {
            try {
                Product product = productService.findById(exportT.getProductExport().getId()).get();
                if(product.getQuantity() >= exportT.getQuantity()){
                    exportT.setDeleted(deleted);
                    exportService.save(exportT);
                    product.setQuantity(product.getQuantity() - exportT.getQuantity());
                    modelAndView.addObject("success", "Export has been successfully updated");
                }else {
                    exportT.setDeleted(deleted);
                    modelAndView.addObject("error", "The number of products is not enough");
                }
            } catch (Exception e) {
                if (!(bindingResult.hasFieldErrors())) {
                    e.printStackTrace();
                    modelAndView.addObject("error", "Invalid data, please contact system administrator");
                }
            }
        }

        modelAndView.setViewName("cp/export/edit");

        return modelAndView;
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView showDelete(@PathVariable long id) {

        ModelAndView modelAndView = new ModelAndView();

        Optional<Export> exportOptional = exportService.findById(id);

        if (exportOptional.isPresent()) {
            if (exportOptional.get().isDeleted()) {
                List<Export> exports = exportService.findAll();
                modelAndView.addObject("exports", exports);
                modelAndView.addObject("error", "Deleted product");
                modelAndView.setViewName("cp/export/list");
            } else {
                modelAndView.addObject("export", exportOptional.get());
                List<Product> products = productService.findAll();
                modelAndView.addObject("products", products);
                modelAndView.setViewName("cp/export/delete");
            }
        } else {
            List<Export> exports = exportService.findAll();
            modelAndView.addObject("exports", exports);
            modelAndView.setViewName("cp/export/list");
        }

        return modelAndView;
    }

    @PostMapping(value = "/delete/{id}")
    public ModelAndView delete(@PathVariable long id) {

        ModelAndView modelAndView = new ModelAndView();
        List<Product> products = productService.findAll();
        modelAndView.addObject("products", products);
        Export anExport = exportService.findById(id).get();

        try {
            if (anExport.isDeleted()){
                modelAndView.addObject("export", anExport);
                modelAndView.addObject("error", "Deleted import");
            }else {
                anExport.setDeleted(true);
                exportService.save(anExport);
                modelAndView.addObject("export", anExport);
                modelAndView.addObject("success", "Export has been successfully deleted");
            }

        } catch (Exception e) {
            modelAndView.addObject("export", anExport);
            modelAndView.addObject("error", "Invalid data, please contact system administrator");
        }

        modelAndView.setViewName("cp/export/delete");

        return modelAndView;
    }
}
