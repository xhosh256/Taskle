package xhosh.dev.Taskle.mapper.createupdate;

import org.springframework.stereotype.Component;
import xhosh.dev.Taskle.dto.auth.UserCreateDto;
import xhosh.dev.Taskle.dto.user.UserUpdateDto;
import xhosh.dev.Taskle.entity.Profile;
import xhosh.dev.Taskle.entity.Role;
import xhosh.dev.Taskle.entity.User;
import xhosh.dev.Taskle.mapper.Mapper;

@Component
public class UserCreateUpdateMapper implements Mapper<UserCreateDto, User> {

    @Override
    public User map(UserCreateDto obj) {
        User user = new User();
        user.setPassword(obj.getPassword());
        user.setUsername(obj.getUsername());
        user.setRole(Role.USER);

        Profile profile = new Profile();
        profile.setFirstname(obj.getFirstname());
        profile.setLastname(obj.getLastname());

        user.setProfile(profile);
        return user;
    }

    public User map(UserUpdateDto obj, User obj2) {
        Profile profile = obj2.getProfile();
        profile.setFirstname(obj.getFirstname());
        profile.setLastname(obj.getLastname());

        return obj2;
    }
}
