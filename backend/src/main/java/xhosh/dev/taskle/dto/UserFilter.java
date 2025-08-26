package xhosh.dev.taskle.dto;

import lombok.Value;
import xhosh.dev.taskle.entity.enumeration.Role;

import java.time.LocalDate;

@Value
public class UserFilter {
    String firstname;
    String lastname;
    LocalDate birthDate;
}
