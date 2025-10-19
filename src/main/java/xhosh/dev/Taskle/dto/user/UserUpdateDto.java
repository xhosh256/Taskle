package xhosh.dev.Taskle.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class UserUpdateDto {

    @NotNull
    @JsonProperty(required = true)
    @NotBlank(message = "firstname cannot be blank")
    String firstname;

    @NotNull
    @NotBlank(message = "lastname cannot be blank")
    @JsonProperty(required = true)
    String lastname;
}
