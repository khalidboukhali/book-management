package ma.norsys.bookmanagementapi.mapper;

import ma.norsys.bookmanagementapi.dto.requestDto.UserRequest;
import ma.norsys.bookmanagementapi.dto.responseDto.UserResponse;
import ma.norsys.bookmanagementapi.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserResponse toDto(User user);
    User toEntity(UserRequest userRequest);
}
