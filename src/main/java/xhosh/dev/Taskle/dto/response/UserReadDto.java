package xhosh.dev.Taskle.dto.response;

import lombok.Value;
import xhosh.dev.Taskle.entity.Role;

@Value
public class UserReadDto {
    Long id;
    String username;
    String firstname;
    String lastname;
    Role role;
}
