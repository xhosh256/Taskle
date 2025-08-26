package xhosh.dev.taskle.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;
import xhosh.dev.taskle.entity.enumeration.Role;

import java.time.LocalDate;

@Value
public class UserCreateEditDto {

    @NotBlank
    @Size(max=128)
    String username;

    @NotBlank
    @Size(max=128)
    String password;

    @NotBlank
    @Size(max=128)
    String firstname;

    @NotBlank
    @Size(max=128)
    String lastname;
    LocalDate birthDate;
    Role role;
}
