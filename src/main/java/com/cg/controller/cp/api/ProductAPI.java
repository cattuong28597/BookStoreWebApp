package com.cg.controller.cp.api;

import com.cg.exception.ResourceNotFoundException;
import com.cg.model.Product;
import com.cg.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("cp/api/product")
public class ProductAPI {

    @Autowired
    private ProductService productService;

    @GetMapping("/getById/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Long id)  {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Can not found product with the Id: " + id);
        }
    }
}
