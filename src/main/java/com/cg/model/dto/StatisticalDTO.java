package com.cg.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticalDTO {

    @NotNull(message = "Ngay bat dau khong duoc de trong")
    private String startDate;

    @NotNull(message = "Ngay ket thuc khong duoc de trong")
    private String endDate;

}
