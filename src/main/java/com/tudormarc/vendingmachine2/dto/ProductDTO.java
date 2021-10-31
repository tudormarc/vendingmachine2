package com.tudormarc.vendingmachine2.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ProductDTO {
    @NotEmpty
    private String productName;

    @NotNull
    private Integer amountAvailable;

    @NotNull
    private BigDecimal cost;
}
