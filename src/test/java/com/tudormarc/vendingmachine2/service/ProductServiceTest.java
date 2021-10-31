package com.tudormarc.vendingmachine2.service;

import com.tudormarc.vendingmachine2.dao.ProductRepository;
import com.tudormarc.vendingmachine2.domain.Product;
import com.tudormarc.vendingmachine2.errors.exceptions.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void testCreateProduct() {
        //given
        Product product = getProduct();

        when(productRepository.addProduct(any())).then(returnsFirstArg());

        //when
        Product returnedProduct = productService.createProduct(product, "testUser");

        //then
        Assertions.assertEquals("testUser", returnedProduct.getSellerId());
    }

    @Test
    public void testGetProductNotFound() {
        //given
        when(productRepository.getProduct(any())).thenReturn(Optional.empty());

        //then
        assertThrows(BadRequestException.class, () -> productService.getProduct("testProduct"));
    }

    @Test
    public void testUpdateProduct() {
        //given
        Product product = getProduct();

        when(productRepository.updateProduct(any())).then(returnsFirstArg());

        //when
        Product returnedProduct = productService.updateProduct(product, "testUser");

        //then
        Assertions.assertEquals("testUser", returnedProduct.getSellerId());
    }

    private Product getProduct() {
        Product product = new Product();
        product.setProductName("testProduct");
        product.setCost(BigDecimal.ZERO);
        product.setAmountAvailable(0);

        return product;
    }
}
