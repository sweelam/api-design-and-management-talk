package com.users.mappers;

import com.users.dto.UserDto;
import com.users.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperSpringConfig.class)
public interface UserMapper {
    @Mapping(source = "passwordHash", target = "password")
    UserDto convertToUserDto(User user);

    @Mapping(source = "password", target = "passwordHash")
    User convertToUserEntity(UserDto userDto);
}
