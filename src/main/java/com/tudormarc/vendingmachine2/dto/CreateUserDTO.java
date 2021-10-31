package com.tudormarc.vendingmachine2.dto;

import com.tudormarc.vendingmachine2.domain.User;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateUserDTO {
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotNull
    private User.UserRole role;
}
