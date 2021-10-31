package com.tudormarc.vendingmachine2.controller;

import com.tudormarc.vendingmachine2.domain.User;
import com.tudormarc.vendingmachine2.dto.CreateUserDTO;
import com.tudormarc.vendingmachine2.dto.ReturnUserDTO;
import com.tudormarc.vendingmachine2.mapper.CreateUserDTOToUserMapper;
import com.tudormarc.vendingmachine2.service.UserService;
import com.tudormarc.vendingmachine2.validation.ValidCents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/vending-machine/user")
@Validated
@Slf4j
public class UserController {

    private final UserService userService;

    private final DefaultTokenServices tokenServices;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, AuthorizationServerTokenServices defaultAuthorizationServerTokenServices, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.tokenServices = (DefaultTokenServices) defaultAuthorizationServerTokenServices;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<ReturnUserDTO> createUser(@RequestBody @Valid CreateUserDTO createUser) {
        createUser.setPassword(passwordEncoder.encode(createUser.getPassword()));
        User user = CreateUserDTOToUserMapper.INSTANCE.createUserDTOToUser(createUser);
        user = userService.createUser(user);

        return new ResponseEntity<>(CreateUserDTOToUserMapper.INSTANCE.userToReturnUserDTO(user), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("#id == principal.username")
    public ResponseEntity<ReturnUserDTO> getUser(@PathVariable String id) {
        User user = userService.getUser(id);

        return new ResponseEntity<>(CreateUserDTOToUserMapper.INSTANCE.userToReturnUserDTO(user), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("#id == principal.username && #id == #updateUser.username")
    public ResponseEntity<ReturnUserDTO> updateUser(@PathVariable String id, @RequestBody @Valid CreateUserDTO updateUser, OAuth2Authentication authentication) {
        User user = CreateUserDTOToUserMapper.INSTANCE.createUserDTOToUser(updateUser);
        user = userService.updateUser(user);

        if (user.isRoleChanged()) {
            //remove access token so user can't perform other calls
            tokenServices.revokeToken(tokenServices.getAccessToken(authentication).getValue());
        }

        return new ResponseEntity<>(CreateUserDTOToUserMapper.INSTANCE.userToReturnUserDTO(user), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("#id == principal.username")
    public ResponseEntity<Void> deleteUser(@PathVariable String id, OAuth2Authentication authentication) {
        userService.deleteUser(id);

        //remove access token so user can't perform other calls
        tokenServices.revokeToken(tokenServices.getAccessToken(authentication).getValue());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "/deposit")
    @PreAuthorize("hasAuthority('BUYER')")
    public ResponseEntity<ReturnUserDTO> deposit(@RequestParam @ValidCents BigDecimal amount, OAuth2Authentication authentication) {
        User user = userService.deposit(amount, authentication.getName());

        return new ResponseEntity<>(CreateUserDTOToUserMapper.INSTANCE.userToReturnUserDTO(user), HttpStatus.OK);
    }

    @PostMapping(path = "/reset")
    @PreAuthorize("hasAuthority('BUYER')")
    public ResponseEntity<ReturnUserDTO> reset(OAuth2Authentication authentication) {
        User user = userService.reset(authentication.getName(), BigDecimal.ZERO);

        return new ResponseEntity<>(CreateUserDTOToUserMapper.INSTANCE.userToReturnUserDTO(user), HttpStatus.OK);
    }
}
