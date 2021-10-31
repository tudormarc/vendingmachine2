package com.tudormarc.vendingmachine2.mapper;

import com.tudormarc.vendingmachine2.domain.User;
import com.tudormarc.vendingmachine2.dto.CreateUserDTO;
import com.tudormarc.vendingmachine2.dto.ReturnUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CreateUserDTOToUserMapper {
    CreateUserDTOToUserMapper INSTANCE = Mappers.getMapper(CreateUserDTOToUserMapper.class);

    User createUserDTOToUser(CreateUserDTO createUserDTO);

    ReturnUserDTO userToReturnUserDTO(User user);

}
