package xhosh.dev.taskle.dto;

import lombok.Value;
import xhosh.dev.taskle.entity.enumeration.Role;

import java.time.LocalDate;

@Value
public class UserCreateEditDto {
    String username;
    String password;
    String firstname;
    String lastname;
    LocalDate birthDate;
    Role role;
}
