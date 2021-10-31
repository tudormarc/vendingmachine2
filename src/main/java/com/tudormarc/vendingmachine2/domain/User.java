package com.tudormarc.vendingmachine2.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class User {
    private String username;
    private String password;
    private BigDecimal deposit = BigDecimal.ZERO;
    private UserRole role;

    private transient boolean roleChanged;

    public enum UserRole {
        SELLER, BUYER;
    }
}


