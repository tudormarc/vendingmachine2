package com.tudormarc.vendingmachine2.controller;

import com.tudormarc.vendingmachine2.dto.BuyingProductDTO;
import com.tudormarc.vendingmachine2.dto.ReturnedBoughtProductDTO;
import com.tudormarc.vendingmachine2.service.VendingMachineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vending-machine/")
@Slf4j
public class VendingMachineController {

    private final VendingMachineService vendingMachineService;

    @Autowired
    public VendingMachineController(VendingMachineService vendingMachineService) {
        this.vendingMachineService = vendingMachineService;
    }

    @PostMapping(path = "buy")
    @PreAuthorize("hasAuthority('BUYER')")
    public ResponseEntity<ReturnedBoughtProductDTO> buy(@RequestBody BuyingProductDTO buyingProduct, OAuth2Authentication authentication) {
        return new ResponseEntity<>(vendingMachineService.buy(buyingProduct, authentication.getName()), HttpStatus.OK);
    }
}
