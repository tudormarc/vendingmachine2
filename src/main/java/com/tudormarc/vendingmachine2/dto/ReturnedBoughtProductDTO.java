package com.tudormarc.vendingmachine2.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReturnedBoughtProductDTO {
    private BigDecimal cost;
    private String productName;
    private Integer amount;
    private BigDecimal change;
}
