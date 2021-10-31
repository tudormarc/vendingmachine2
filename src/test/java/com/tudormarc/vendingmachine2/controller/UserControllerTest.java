package com.tudormarc.vendingmachine2.controller;

import com.tudormarc.vendingmachine2.domain.User;
import com.tudormarc.vendingmachine2.dto.CreateUserDTO;
import com.tudormarc.vendingmachine2.dto.ReturnUserDTO;
import com.tudormarc.vendingmachine2.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

import java.math.BigDecimal;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private DefaultTokenServices tokenServices;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private OAuth2Authentication authentication;

    @Mock
    private OAuth2AccessToken accessToken;

    @InjectMocks
    private UserController userController;

    @Test
    public void testCreateUserSuccessfully() {
        //given
        when(userService.createUser(any())).then(returnsFirstArg());
        CreateUserDTO createUserDTO = getCreateUser();

        //when
        ResponseEntity<ReturnUserDTO> userResponse = userController.createUser(createUserDTO);

        //then
        Assertions.assertEquals(HttpStatus.CREATED, userResponse.getStatusCode());
        Assertions.assertEquals(createUserDTO.getUsername(), userResponse.getBody().getUsername());
        Assertions.assertEquals(createUserDTO.getRole(), userResponse.getBody().getRole());
        Assertions.assertEquals(BigDecimal.ZERO, userResponse.getBody().getDeposit());
    }

    @Test
    public void testGetUserSuccessfully() {
        //given
        User user = getUser();

        when(userService.getUser("testUser")).thenReturn(user);


        //when
        ResponseEntity<ReturnUserDTO> userResponse = userController.getUser("testUser");

        //then
        Assertions.assertEquals(HttpStatus.OK, userResponse.getStatusCode());
        Assertions.assertEquals(user.getUsername(), userResponse.getBody().getUsername());
        Assertions.assertEquals(user.getRole(), userResponse.getBody().getRole());
    }

    @Test
    public void testUpdateUserSuccessfully() {
        //given
        when(userService.updateUser(any())).then(returnsFirstArg());
        CreateUserDTO createUserDTO = getCreateUser();

        //when
        ResponseEntity<ReturnUserDTO> userResponse = userController.updateUser("testUser", createUserDTO, authentication);

        //then
        Assertions.assertEquals(HttpStatus.OK, userResponse.getStatusCode());
        Assertions.assertEquals(createUserDTO.getUsername(), userResponse.getBody().getUsername());
        Assertions.assertEquals(createUserDTO.getRole(), userResponse.getBody().getRole());
        Assertions.assertEquals(BigDecimal.ZERO, userResponse.getBody().getDeposit());
    }

    @Test
    public void testDeleteUserSuccessfully() {
        //given
        when(tokenServices.getAccessToken(any())).thenReturn(accessToken);

        //when
        ResponseEntity<Void> userResponse = userController.deleteUser("testUser", authentication);

        //then
        Assertions.assertEquals(HttpStatus.NO_CONTENT, userResponse.getStatusCode());
    }

    @Test
    public void testDepositSuccessfully() {
        //given
        User user = getUser();

        when(userService.deposit(any(), any())).thenReturn(user);


        //when
        ResponseEntity<ReturnUserDTO> userResponse = userController.deposit(BigDecimal.ZERO, authentication);

        //then
        Assertions.assertEquals(HttpStatus.OK, userResponse.getStatusCode());
        Assertions.assertEquals(user.getUsername(), userResponse.getBody().getUsername());
        Assertions.assertEquals(user.getRole(), userResponse.getBody().getRole());
    }

    @Test
    public void testResetSuccessfully() {
        //given
        User user = getUser();

        when(userService.reset(user.getUsername(), BigDecimal.ZERO)).thenReturn(user);
        when(authentication.getName()).thenReturn(user.getUsername());

        //when
        ResponseEntity<ReturnUserDTO> userResponse = userController.reset(authentication);

        //then
        Assertions.assertEquals(HttpStatus.OK, userResponse.getStatusCode());
        Assertions.assertEquals(user.getUsername(), userResponse.getBody().getUsername());
        Assertions.assertEquals(user.getRole(), userResponse.getBody().getRole());
    }

    private CreateUserDTO getCreateUser() {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setUsername("testUser");
        createUserDTO.setPassword("testPassword");
        createUserDTO.setRole(User.UserRole.SELLER);

        return createUserDTO;
    }

    private User getUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setDeposit(BigDecimal.ZERO);
        user.setRole(User.UserRole.SELLER);

        return user;
    }
}
