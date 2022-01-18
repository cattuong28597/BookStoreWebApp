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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
                int quan = importService.sumQuatityWithIdProduct(product.getId());
                int quanE = exportService.sumQuatityWithIdProduct(product.getId());

                if((quan - quanE) >= export.getQuantity()){

                    product.setQuantity(quan - quanE - export.getQuantity());
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
}
