package com.cg.model;

import com.cg.model.dto.CartDetailDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cart_details")
public class CartDetail extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    private Cart cart;

    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @Min(value = 1, message = "Số lượng không được nhỏ hơn 1")
    private int quantity;

    public CartDetailDTO toCartDetailDTO() {
        CartDetailDTO cartDetail = new CartDetailDTO();
        cartDetail.setId(id);
        cartDetail.setIdCart(cart.getId());
        cartDetail.setIdProduct(product.getId());
        cartDetail.setName(product.getName());
        cartDetail.setQuantity(quantity);
        return cartDetail;
    }

}
