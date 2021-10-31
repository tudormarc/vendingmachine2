package com.tudormarc.vendingmachine2.service;

import com.tudormarc.vendingmachine2.domain.Product;
import com.tudormarc.vendingmachine2.domain.User;
import com.tudormarc.vendingmachine2.dto.BuyingProductDTO;
import com.tudormarc.vendingmachine2.dto.ReturnedBoughtProductDTO;
import com.tudormarc.vendingmachine2.errors.exceptions.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VendingMachineServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private VendingMachineService vendingMachineService;

    @Test
    public void testBuyProductNotEnoughAmount() {
        //given
//        User user = new User();
//        user.setDeposit(BigDecimal.TEN);
        BuyingProductDTO buyingProductDTO = new BuyingProductDTO();
        buyingProductDTO.setAmount(5);

        Product product = new Product();
//        product.setCost(new BigDecimal(15));
        product.setAmountAvailable(2);
        when(productService.getProduct(any())).thenReturn(product);

        //then
        Assertions.assertThrows(BadRequestException.class, () -> vendingMachineService.buy(buyingProductDTO, "testUser"));
    }

    @Test
    public void testBuyProductUserNotEnoughDeposit() {
        //given
        User user = new User();
        user.setDeposit(BigDecimal.TEN);
        when(userService.getUser(any())).thenReturn(user);

        Product product = new Product();
        product.setCost(new BigDecimal(15));
        product.setAmountAvailable(2);
        when(productService.getProduct(any())).thenReturn(product);

        BuyingProductDTO buyingProductDTO = new BuyingProductDTO();
        buyingProductDTO.setAmount(1);

        //then
        Assertions.assertThrows(BadRequestException.class, () -> vendingMachineService.buy(buyingProductDTO, "testUser"));
    }

    @Test
    public void testBuyProductCheckChange() {
        //given
        User user = new User();
        user.setDeposit(new BigDecimal(22));
        when(userService.getUser(any())).thenReturn(user);

        Product product = new Product();
        product.setCost(new BigDecimal(15));
        product.setAmountAvailable(2);
        when(productService.getProduct(any())).thenReturn(product);

        BuyingProductDTO buyingProductDTO = new BuyingProductDTO();
        buyingProductDTO.setAmount(1);

        //when
        ReturnedBoughtProductDTO response = vendingMachineService.buy(buyingProductDTO, "testUser");

        //then
        Assertions.assertEquals(new BigDecimal(5), response.getChange());
    }
}
