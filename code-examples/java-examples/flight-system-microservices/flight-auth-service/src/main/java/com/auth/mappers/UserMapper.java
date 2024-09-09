package com.auth.mappers;

import com.auth.dto.UserDto;
import com.auth.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperSpringConfig.class)
public interface UserMapper {
    @Mapping(source = "passwordHash", target = "password")
    UserDto convertToUserDto(User user);

    @Mapping(source = "password", target = "passwordHash")
    User convertToUserEntity(UserDto userDto);
}
