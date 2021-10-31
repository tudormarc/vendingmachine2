package com.tudormarc.vendingmachine2.service;

import com.tudormarc.vendingmachine2.domain.Product;
import com.tudormarc.vendingmachine2.domain.User;
import com.tudormarc.vendingmachine2.dto.BuyingProductDTO;
import com.tudormarc.vendingmachine2.dto.ReturnedBoughtProductDTO;
import com.tudormarc.vendingmachine2.errors.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class VendingMachineService {

    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public VendingMachineService(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    public ReturnedBoughtProductDTO buy(@RequestBody BuyingProductDTO buyingProduct, String username) {
        User user = userService.getUser(username);
        Product product = productService.getProduct(buyingProduct.getProductId());

        ReturnedBoughtProductDTO returnedBoughtProduct = new ReturnedBoughtProductDTO();
        if (product.getAmountAvailable() < buyingProduct.getAmount()) {
            throw new BadRequestException("Product amount is not enough: " + buyingProduct.getProductId());
        }

        BigDecimal cost = product.getCost().multiply(new BigDecimal(buyingProduct.getAmount()));
        if (user.getDeposit().compareTo(cost) < 0) {
            throw new BadRequestException("User does not have enough deposit, need " + cost.subtract(user.getDeposit()) + " more");
        }

        returnedBoughtProduct.setProductName(product.getProductName());
        returnedBoughtProduct.setAmount(buyingProduct.getAmount());
        returnedBoughtProduct.setCost(cost);
        BigDecimal change = user.getDeposit().subtract(cost).divide(new BigDecimal(5), RoundingMode.DOWN).multiply(new BigDecimal(5));
        returnedBoughtProduct.setChange(change);

        BigDecimal reset = user.getDeposit().subtract(cost).subtract(change);
        userService.reset(username, reset);

        return returnedBoughtProduct;
    }
}
