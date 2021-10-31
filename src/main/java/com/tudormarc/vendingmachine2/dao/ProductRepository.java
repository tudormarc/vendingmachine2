package com.tudormarc.vendingmachine2.dao;

import com.tudormarc.vendingmachine2.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class ProductRepository {

    private final Map<String, Product> products = new HashMap<>();

    public Product addProduct(Product product) {
        products.put(product.getProductName().toLowerCase(Locale.ROOT), product);
        return product;
    }

    public Optional<Product> getProduct(String productName) {
        return Optional.ofNullable(products.get(productName.toLowerCase(Locale.ROOT)));
    }

    public Product updateProduct(Product product) {
        products.put(product.getProductName().toLowerCase(Locale.ROOT), product);
        return product;
    }

    public void deleteProduct(String productName) {
        products.remove(productName.toLowerCase(Locale.ROOT));
    }
}
