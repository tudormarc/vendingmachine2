package com.tudormarc.vendingmachine2.controller;

import com.tudormarc.vendingmachine2.domain.Product;
import com.tudormarc.vendingmachine2.dto.ProductDTO;
import com.tudormarc.vendingmachine2.mapper.ProductDTOToProductMapper;
import com.tudormarc.vendingmachine2.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/vending-machine/product")
@Validated
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO createProductDTO, OAuth2Authentication authentication) {
        String username = authentication.getName();
        Product product = ProductDTOToProductMapper.INSTANCE.productDTOToProduct(createProductDTO);

        product = productService.createProduct(product, username);

        return new ResponseEntity<>(ProductDTOToProductMapper.INSTANCE.productToProductDTO(product), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{productName}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable String productName) {
        Product product = productService.getProduct(productName);

        return new ResponseEntity<>(ProductDTOToProductMapper.INSTANCE.productToProductDTO(product), HttpStatus.OK);
    }

    @PutMapping(path = "/{productName}")
    @PreAuthorize("#productName == #productDTO.productName && @productService.getProduct(#productName) != null && " +
            "@productService.getProduct(#productName).getSellerId() == principal.username")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable String productName, @RequestBody @Valid ProductDTO productDTO, OAuth2Authentication authentication) {
        String username = authentication.getName();
        Product product = ProductDTOToProductMapper.INSTANCE.productDTOToProduct(productDTO);

        product = productService.updateProduct(product, username);

        return new ResponseEntity<>(ProductDTOToProductMapper.INSTANCE.productToProductDTO(product), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{productName}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("@productService.getProduct(#productName) != null && " +
            "@productService.getProduct(#productName).getSellerId() == principal.username")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productName) {
        productService.deleteProduct(productName);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
