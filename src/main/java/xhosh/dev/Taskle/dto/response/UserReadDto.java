package xhosh.dev.Taskle.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xhosh.dev.Taskle.entity.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReadDto {
    Long id;
    String username;
    String firstname;
    String lastname;
    Role role;
}
