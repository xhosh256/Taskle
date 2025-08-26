package xhosh.dev.taskle.http.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import xhosh.dev.taskle.dto.*;
import xhosh.dev.taskle.service.UserService;


@Slf4j
@RestController
@ResponseBody
@RequestMapping("/users")
@AllArgsConstructor
public class UserRestController {

    private UserService userService;


    @GetMapping
    public PageResponse<UserReadDto> findAll(UserFilter filter, Pageable pageable) {
        return PageResponse
                .of(userService.findAll(filter, pageable));
    }

    @GetMapping("/{id}")
    public UserReadDto findById(@PathVariable("id") Long id) {
        return userService
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDto create(@RequestBody @Valid UserCreateEditDto createEditDto) {
        return userService.create(createEditDto);
    }

    @PutMapping("/{id}")
    public UserReadDto update(
            @PathVariable("id") Long id,
            @RequestBody @Valid UserCreateEditDto createEditDto
    ) {
        return userService
                .update(id, createEditDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if(!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @ExceptionHandler(Exception.class)
    public Exception handler(Exception exception) {
        log.info("Произошел пиздец: {}", exception.getMessage());
        return exception;
    }
}
