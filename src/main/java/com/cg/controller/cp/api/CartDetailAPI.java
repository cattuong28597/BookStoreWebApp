package com.cg.controller.cp.api;

import com.cg.exception.DataInputException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.model.CartDetail;
import com.cg.model.dto.CartDetailDTO;
import com.cg.service.cart.CartService;
import com.cg.service.cartdetail.CartDetailService;
import com.cg.service.product.ProductService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("cp/api/cart-details")
public class CartDetailAPI {
    @Autowired
    private CartDetailService cartDetailService;

    @Autowired
    private CartService cartService;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Iterable<?>> findAll() {
        try {
            List<CartDetail> cartDetails = cartDetailService.findAll();
            List<CartDetailDTO> cartDetailsDTO = null;

            for (CartDetail cartDetail: cartDetails) {
                cartDetailsDTO.add(cartDetail.toCartDetailDTO());
            }

            if (cartDetails.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(cartDetailsDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/cart/{id}")
    public ResponseEntity<Iterable<?>> showCpCartDetailsIndex(@PathVariable Long id) {
        try {
            List<CartDetail> cartDetails = cartDetailService.findCartDetailByCartAndDeletedIsFalse(id);

            if (cartDetails.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(cartDetails, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> createCartDetail(@Validated @RequestBody CartDetail cartDetail, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return appUtils.mapErrorToResponse(bindingResult);

        try {
            cartDetail.setQuantity(1);
            CartDetail cartDetail1 = cartDetailService.save(cartDetail);

            return new ResponseEntity<>(cartDetail1, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Invalid cart detail creation information");
        }
    }

    @GetMapping("/edit/cart-detail/{id}")
    public ResponseEntity<?> showEdit(@PathVariable Long id) {

        Optional<CartDetail> cartDetail = cartDetailService.findById(id);

//        return cartDetail.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
////        Viết tắt

        if (cartDetail.isPresent()) {
            return new ResponseEntity<>(cartDetail.get(), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("No cart detail found with the Id: " + id);
        }
    }

    @PutMapping(value = "/edit/cart-detail/{id}/{quantity}")
    public ResponseEntity<?> updateQuantity(@PathVariable Long id, @PathVariable int quantity) {

        try {
            CartDetail updateCartDetail = cartDetailService.findById(id).get();
            updateCartDetail.setQuantity(quantity);
            cartDetailService.save(updateCartDetail);
            return new ResponseEntity<>(updateCartDetail, HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Invalid cart detail update information");
        }
    }

    @PutMapping(value = "/edit/cart-detail/{id}")
    public ResponseEntity<?> update(@Validated CartDetail cartDetail, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return appUtils.mapErrorToResponse(bindingResult);

        try {
            CartDetail updateCartDetail = cartDetailService.save(cartDetail);

            return new ResponseEntity<>(updateCartDetail, HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            throw new DataInputException("Invalid cart detail update information");
        }
    }

    @GetMapping("/sum/cart/{id}")
    public BigDecimal getSumPrice(@PathVariable Long id) {

        try {
            List<CartDetail> cartDetails = cartDetailService.findCartDetailByCartAndDeletedIsFalse(id);
            if (cartDetails.isEmpty()) {
                return new BigDecimal(0);
            }
            BigDecimal sumCart = BigDecimal.valueOf(0);
            for (CartDetail item: cartDetails) {
                BigDecimal tempQ = new BigDecimal(Integer.toString(item.getQuantity()));
                BigDecimal b = item.getProduct().getLastPrice();

                sumCart = b.multiply(tempQ);
            }

            return sumCart;
        } catch (Exception e) {
            return new BigDecimal(0);
        }
    }

    @DeleteMapping("/suspend/cart-detail/{id}")
    public ResponseEntity<?> doSuspend(@PathVariable Long id) {
        Optional<CartDetail> cartDetail = cartDetailService.findById(id);

        if (cartDetail.isPresent()) {
            try {
                cartDetail.get().setDeleted(true);
                cartDetailService.save(cartDetail.get());

                return new ResponseEntity<>(HttpStatus.CREATED);
            } catch (DataIntegrityViolationException e) {
                throw new DataInputException("Invalid suspension information");
            }
        } else {
            throw new DataInputException("Invalid cart detail information");
        }
    }
}
