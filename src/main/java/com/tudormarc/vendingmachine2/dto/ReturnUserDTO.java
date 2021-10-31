package com.tudormarc.vendingmachine2.dto;

import com.tudormarc.vendingmachine2.domain.User;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReturnUserDTO {
    private String username;
    private BigDecimal deposit;
    private User.UserRole role;
}
