package xhosh.dev.Taskle.dto.other;

import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
public class Error {

    HttpStatus status;
    String message;
}
