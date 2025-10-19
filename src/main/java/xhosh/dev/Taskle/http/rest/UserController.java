package xhosh.dev.Taskle.http.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xhosh.dev.Taskle.dto.response.UserReadDto;
import xhosh.dev.Taskle.dto.user.UserUpdateDto;
import xhosh.dev.Taskle.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@ResponseBody
public class UserController {

    private UserService userService;

    @GetMapping("/me")
    private UserReadDto findMe() {
        return userService.findMe();
    }

    @GetMapping("/{username}")
    public UserReadDto findByUsername(@PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    @PutMapping
    public UserReadDto update(@Valid @RequestBody UserUpdateDto updateDto) {
        return userService.update(updateDto);
    }

    @DeleteMapping
    public ResponseEntity<String> delete() {
       userService.delete();
       return ResponseEntity.ok("ACCOUNT WAS DELETED");
    }
}
