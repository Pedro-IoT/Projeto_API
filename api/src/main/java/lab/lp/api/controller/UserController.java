package lab.lp.api.controller;

import lab.lp.api.dto.UserCreateDTO;
import lab.lp.api.dto.UserResponseDTO;
import lab.lp.api.model.User;
import lab.lp.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO body) {
        UserResponseDTO newUser = userService.create(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}
