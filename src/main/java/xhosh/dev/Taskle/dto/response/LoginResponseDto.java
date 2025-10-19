package xhosh.dev.Taskle.dto.response;

import lombok.Value;

@Value
public class LoginResponseDto {
    Long id;
    String username;
    String firstname;
    String lastname;
    String token;
}
