package com.tudormarc.vendingmachine2.mapper;

import com.tudormarc.vendingmachine2.domain.Product;
import com.tudormarc.vendingmachine2.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductDTOToProductMapper {
    ProductDTOToProductMapper INSTANCE = Mappers.getMapper(ProductDTOToProductMapper.class);

    Product productDTOToProduct(ProductDTO productDTO);

    ProductDTO productToProductDTO(Product product);
}
