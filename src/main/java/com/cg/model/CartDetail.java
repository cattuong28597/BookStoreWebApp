package com.cg.model;

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
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @Min(value = 1, message = "Số lượng không được nhỏ hơn 1")
    private int quantity;

//    @Digits(integer = 12, fraction = 2)
//    @Column(name = "price", nullable= false)
//    private BigDecimal total = BigDecimal.valueOf(0);
//
//    public CartDetail(Long id, Cart cart, Product product, int quantity) {
//        this.id = id;
//        this.cart = cart;
//        this.product = product;
//        this.quantity = quantity;
//    }
}
