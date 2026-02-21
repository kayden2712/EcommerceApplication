package org.example.ecommerceapplication.user.mapper;

import org.example.ecommerceapplication.user.dto.UserResponse;
import org.example.ecommerceapplication.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", expression = "java(user.getRole().name())")
    UserResponse toResponse(User user);
}

