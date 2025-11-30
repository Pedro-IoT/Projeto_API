package lab.lp.api.controller;

import lab.lp.api.model.User;
import lab.lp.api.model.Habit;
import lab.lp.api.service.HabitService;
import lab.lp.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private HabitService habitService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.create(user);
    }
}
