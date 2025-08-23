package xhosh.dev.taskle.mapper;

import xhosh.dev.taskle.dto.UserCreateEditDto;
import xhosh.dev.taskle.entity.Profile;
import xhosh.dev.taskle.entity.User;

public class UserCreateEditDtoMapper implements Mapper<UserCreateEditDto, User> {

    // for create
    @Override
    public User map(UserCreateEditDto from) {
        Profile profile = new Profile();
        profile.setFirstname(from.getFirstname());
        profile.setLastname(from.getLastname());
        profile.setBirthDate(from.getBirthDate());

        User user = new User();

        user.setProfile(profile);
        user.setRole(from.getRole());
        user.setPassword(from.getPassword());
        user.setUsername(from.getUsername());

        return user;
    }

    // for update
    @Override
    public User map(UserCreateEditDto from, User updated) {
        updated.setUsername(from.getUsername());
        updated.setPassword(from.getPassword());
        updated.getProfile().setFirstname(from.getFirstname());
        updated.getProfile().setLastname(from.getLastname());
        updated.getProfile().setBirthDate(from.getBirthDate());
        updated.setRole(from.getRole());
        return updated;
    }
}
