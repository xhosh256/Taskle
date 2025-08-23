package xhosh.dev.taskle.http.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import xhosh.dev.taskle.dto.UserCreateEditDto;
import xhosh.dev.taskle.dto.UserReadDto;
import xhosh.dev.taskle.service.UserService;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/users")
@AllArgsConstructor
public class UserRestController {

    private UserService userService;

//    @GetMapping
//    private List<UserReadDto> findAll(UserFilter filter) {
//        return userService.findAll(filter);
//    }

    @GetMapping
    private List<UserReadDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    private UserReadDto findById(@PathVariable("id") Long id) {
        return userService
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private UserReadDto create(@RequestBody UserCreateEditDto createEditDto) {
        return userService.create(createEditDto);
    }

    @PutMapping("/{id}")
    private UserReadDto update(
            @PathVariable Long id,
            @RequestBody UserCreateEditDto createEditDto
    ) {
        return userService
                .update(id, createEditDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id) {
        if(!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
