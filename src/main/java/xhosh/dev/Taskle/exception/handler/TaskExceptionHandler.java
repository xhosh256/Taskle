package xhosh.dev.Taskle.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import xhosh.dev.Taskle.exception.exceptions.task.TaskAccessException;
import xhosh.dev.Taskle.exception.exceptions.task.TaskNotFoundException;
import xhosh.dev.Taskle.dto.other.Error;

@ControllerAdvice
public class TaskExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Error> handleTaskNotFound(Exception ex) {
        Error err = new Error(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(TaskAccessException.class)
    public ResponseEntity<Error> handleTaskAccess(Exception ex) {
        Error err = new Error(
                HttpStatus.FORBIDDEN,
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }
}
