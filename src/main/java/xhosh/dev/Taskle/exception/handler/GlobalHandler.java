package xhosh.dev.Taskle.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import xhosh.dev.Taskle.dto.other.Error;

@ControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Error> handleJSONException(Exception exception) {
        Error err = new Error(
                HttpStatus.BAD_REQUEST,
                exception.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
