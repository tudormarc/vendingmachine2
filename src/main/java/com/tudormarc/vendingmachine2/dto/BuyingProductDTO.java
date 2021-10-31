package com.tudormarc.vendingmachine2.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class BuyingProductDTO {
    @NotEmpty
    private String productId;

    @NotNull
    private Integer amount;
}
