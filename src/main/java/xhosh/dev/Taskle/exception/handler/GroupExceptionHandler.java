package xhosh.dev.Taskle.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import xhosh.dev.Taskle.dto.other.Error;
import xhosh.dev.Taskle.exception.exceptions.group.GroupAccessDeniedException;
import xhosh.dev.Taskle.exception.exceptions.group.GroupCountExceeded;
import xhosh.dev.Taskle.exception.exceptions.group.GroupNotFoundException;
import xhosh.dev.Taskle.exception.exceptions.group.TaskAlreadyAddedException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GroupExceptionHandler {

    @ExceptionHandler(GroupNotFoundException.class)
    public ResponseEntity<Error> handleGroupNotFound(Exception e) {
        Error error = new Error(
                HttpStatus.BAD_REQUEST,
                e.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(GroupAccessDeniedException.class)
    public ResponseEntity<Error> handleGroupAccessDenied(Exception e) {
        Error error = new Error(
                HttpStatus.FORBIDDEN,
                e.getMessage()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(GroupCountExceeded.class)
    public ResponseEntity<Error> handleGroupCountExceeded(Exception e) {
        Error error = new Error(
                HttpStatus.CONFLICT,
                e.getMessage()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(TaskAlreadyAddedException.class)
    public ResponseEntity<Error> handleTaskAlreadyAdded(Exception e) {
        Error error = new Error(
                HttpStatus.CONFLICT,
                e.getMessage()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Error> handleNoSuchElementException(Exception e) {
        Error error = new Error(
                HttpStatus.NOT_FOUND,
                e.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
