package com.cg.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Pattern(regexp = "^[\\pL .,0-9()_:-]{2,50}$", message = "Length of product's name must be from 2 to 50 Without special character!")
    private String name;

    @Column(unique = true)
    private String slug;

    @NotBlank(message = "Author name can not be blank")
    private String author;

    @PositiveOrZero (message = "Amount of product can not be negative!")
    @Max(value = 999999999, message = "Amount of product can not more than 1 billion!")
    private int quantity = 0;

    @NotBlank(message = "Publishing company can not be blank")
    private String publishingCompany;

    private Date publicationDate;

    @NotNull(message = "Page can not be blank")
    @Min(value = 1, message = "Amount of Page can not less than 1")
    private int page;

    private double vote = 0.0;

    private String comment ="" ;

    @Digits(integer = 12, fraction = 2)
    @Column(name = "price", nullable= false)
    private BigDecimal price = BigDecimal.valueOf(0);

    @Digits(integer = 3, fraction = 0)
    @Min(value = 0, message = "Percentage discount can not less than 0!")
    @Max(value = 100, message = "Percentage discount can not more than 100%!")
    @Column(name = "percentage_discount", nullable= false)
    private BigDecimal percentageDiscount = BigDecimal.valueOf(0);

    @Digits(integer = 12, fraction = 2)
    @Min(value = 0, message = "Discount Amount can not less than 0!")
    @Column(name = "discount_amount", nullable= false)
    private BigDecimal discountAmount = BigDecimal.valueOf(0);

    @Digits(integer = 12, fraction = 2)
    @Column(name = "last_price", nullable= false)
    private BigDecimal lastPrice = BigDecimal.valueOf(0);

    private Integer rewardPoint = 0;

    private Integer views = 0;

    @Lob
    private String description;

    private String avatar;

    @Column(columnDefinition = "BIGINT(20) DEFAULT 0")
    private Long ts = new Date().getTime();

    @ManyToOne
    @JoinColumn(name = "category_group_id", referencedColumnName = "id", nullable = false)
    private CategoryGroup categoryGroup;


    @JsonIgnore
    @OneToMany(targetEntity = Import.class, mappedBy = "productImport")
    private List<Import> productsImport;

    @JsonIgnore
    @OneToMany(targetEntity = Export.class, mappedBy = "productExport")
    private List<Export> productsExport;

    @JsonIgnore
    @OneToMany(targetEntity = ProductImage.class, mappedBy = "product")
    private List<ProductImage> productImages;
}
