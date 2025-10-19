package xhosh.dev.Taskle.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class UserCreateDto {

    @NotNull
    @JsonProperty(required = true)
    @NotBlank(message = "username cannot be blank")
    @Size(max = 128, message = "username must be at most 128 symbols")
    String username;

    @NotNull
    @JsonProperty(required = true)
    @Size(min = 5, max = 128, message = "password must be at least 5 symbols and at most 128 symbols")
    String password;

    @NotNull
    @JsonProperty(required = true)
    @NotBlank(message = "firstname cannot be blank")
    String firstname;

    @NotNull
    @JsonProperty(required = true)
    @NotBlank(message = "lastname cannot be blank")
    String lastname;
}
