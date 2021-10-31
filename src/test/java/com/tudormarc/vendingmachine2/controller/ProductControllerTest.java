package com.tudormarc.vendingmachine2.controller;

import com.tudormarc.vendingmachine2.domain.Product;
import com.tudormarc.vendingmachine2.dto.ProductDTO;
import com.tudormarc.vendingmachine2.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.math.BigDecimal;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private OAuth2Authentication authentication;

    @InjectMocks
    private ProductController productController;

    @Test
    public void testCreateProduct() {
        //given
        ProductDTO productDTO = getProductDTO();

        when(authentication.getName()).thenReturn("testUser");
        when(productService.createProduct(any(), eq("testUser"))).then(returnsFirstArg());

        //when
        ResponseEntity<ProductDTO> response = productController.createProduct(productDTO, authentication);

        //then
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(productDTO.getProductName(), response.getBody().getProductName());
        Assertions.assertEquals(productDTO.getCost(), response.getBody().getCost());
        Assertions.assertEquals(productDTO.getAmountAvailable(), response.getBody().getAmountAvailable());
    }

    @Test
    public void testGetProduct() {
        //given
        Product product = new Product();
        product.setProductName("testProduct");
        product.setCost(BigDecimal.ZERO);
        product.setAmountAvailable(0);
        product.setSellerId("testUser");

        when(productService.getProduct(any())).thenReturn(product);

        //when
        ResponseEntity<ProductDTO> response = productController.getProduct("testProduct");

        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(product.getProductName(), response.getBody().getProductName());
        Assertions.assertEquals(product.getCost(), response.getBody().getCost());
        Assertions.assertEquals(product.getAmountAvailable(), response.getBody().getAmountAvailable());
    }

    @Test
    public void testUpdateProduct() {
        //given
        ProductDTO productDTO = getProductDTO();

        when(authentication.getName()).thenReturn("testUser");
        when(productService.updateProduct(any(), eq("testUser"))).then(returnsFirstArg());

        //when
        ResponseEntity<ProductDTO> response = productController.updateProduct("testProduct", productDTO, authentication);

        //then
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(productDTO.getProductName(), response.getBody().getProductName());
        Assertions.assertEquals(productDTO.getCost(), response.getBody().getCost());
        Assertions.assertEquals(productDTO.getAmountAvailable(), response.getBody().getAmountAvailable());
    }

    @Test
    public void testDeleteProduct() {
        //given

        //when
        ResponseEntity<Void> response = productController.deleteProduct("testProduct");

        //then
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    private ProductDTO getProductDTO() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("testProduct");
        productDTO.setCost(BigDecimal.ZERO);
        productDTO.setAmountAvailable(0);

        return productDTO;
    }
}
