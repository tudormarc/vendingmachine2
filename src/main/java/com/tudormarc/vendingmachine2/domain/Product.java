package com.tudormarc.vendingmachine2.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {
    private String productName;
    private String sellerId;
    private Integer amountAvailable = 0;
    private BigDecimal cost;
}
