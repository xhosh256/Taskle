package xhosh.dev.taskle.mapper;

import xhosh.dev.taskle.dto.UserReadDto;
import xhosh.dev.taskle.entity.User;

public class UserReadDtoMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User object) {
        return new UserReadDto(
                object.getId(),
                object.getUsername(),
                object.getProfile().getFirstname(),
                object.getProfile().getLastname(),
                object.getProfile().getBirthDate(),
                object.getRole()
        );
    }
}
