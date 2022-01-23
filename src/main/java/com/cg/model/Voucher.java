package com.cg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vouchers")
public class Voucher extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull(message = "Tên voucher  không được để trống ")
    private String name ;

    @NotNull(message = "Mô tả về voucher không được để trống !")
    private String description ;

    @Min(value = 1 ,message = "Giảm giá thấp nhất 1%")
    @Max(value = 99, message = "Giảm giá lớn nhất 99%")
    private int percentageDiscount;

    @JsonIgnore
    @OneToMany(targetEntity = Bill.class, mappedBy = "voucher")
    private List<Bill> bills;
}
