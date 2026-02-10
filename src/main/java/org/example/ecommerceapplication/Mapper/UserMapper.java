package org.example.ecommerceapplication.Mapper;

import org.example.ecommerceapplication.dto.Response.user.UserResponse;
import org.example.ecommerceapplication.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", expression = "java(user.getRole().name())")
    UserResponse toResponse(User user);
}

