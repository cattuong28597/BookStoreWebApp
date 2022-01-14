package com.cg.model.dto;

import com.cg.model.Cart;
import com.cg.model.CartDetail;
import com.cg.model.Product;
import com.cg.model.User;
import com.cg.repository.CartRepository;
import com.cg.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Component
public class CartDetailDTO {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    private Long id;
    private Long idCart;
    private Long idProduct;
    private String name;
    private BigDecimal lastPrice;
    private int quantity;

    public CartDetailDTO(Long id, Cart cart, Product product, int quantity) {
        this.id = id;
        this.idCart = cart.getId();
        this.idProduct = product.getId();
        this.name = product.getName();
        this.setLastPrice(lastPrice);
        this.quantity = quantity;
    }

    public CartDetail toCartDetail() {
        CartDetail cartDetail = new CartDetail();
        cartDetail.setId(id);
        cartDetail.setCart(cartRepository.getById(idCart));
        cartDetail.setProduct(productRepository.getById(idProduct));
        cartDetail.setQuantity(quantity);
        return cartDetail;
    }
}
