package com.tudormarc.vendingmachine2.controller;

import com.tudormarc.vendingmachine2.dto.BuyingProductDTO;
import com.tudormarc.vendingmachine2.dto.ReturnedBoughtProductDTO;
import com.tudormarc.vendingmachine2.service.VendingMachineService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VendingMachineControllerTest {

    @Mock
    private VendingMachineService vendingMachineService;

    @Mock
    private OAuth2Authentication authentication;

    @InjectMocks
    private VendingMachineController vendingMachineController;

    @Test
    public void testBuyProduct() {
        //given
        BuyingProductDTO buyingProductDTO = new BuyingProductDTO();
        buyingProductDTO.setProductId("testId");
        buyingProductDTO.setAmount(0);

        when(authentication.getName()).thenReturn("testUser");

        //when
        ResponseEntity<ReturnedBoughtProductDTO> response = vendingMachineController.buy(buyingProductDTO, authentication);

        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
