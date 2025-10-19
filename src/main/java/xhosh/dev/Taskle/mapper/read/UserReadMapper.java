package xhosh.dev.Taskle.mapper.read;

import org.springframework.stereotype.Component;
import xhosh.dev.Taskle.dto.response.UserReadDto;
import xhosh.dev.Taskle.entity.User;
import xhosh.dev.Taskle.mapper.Mapper;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {
    @Override
    public UserReadDto map(User obj) {
        var profile = obj.getProfile();
        return new UserReadDto(
                obj.getId(),
                obj.getUsername(),
                profile.getFirstname(),
                profile.getLastname(),
                obj.getRole());
    }
}
