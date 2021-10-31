package com.tudormarc.vendingmachine2.service;

import com.tudormarc.vendingmachine2.dao.ProductRepository;
import com.tudormarc.vendingmachine2.domain.Product;
import com.tudormarc.vendingmachine2.errors.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product createProduct, String username) {
        createProduct.setSellerId(username);

        return productRepository.addProduct(createProduct);
    }

    public Product getProduct(String productName) {
        Optional<Product> product = productRepository.getProduct(productName);

        return product.orElseThrow(() -> new BadRequestException("Product not found: " + productName));
    }

    public void deleteProduct(String productName) {
        productRepository.deleteProduct(productName);
    }

    public Product updateProduct(Product updateProduct, String username) {
        updateProduct.setSellerId(username);

        return productRepository.updateProduct(updateProduct);
    }


}
