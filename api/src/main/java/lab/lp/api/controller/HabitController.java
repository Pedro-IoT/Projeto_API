package lab.lp.api.controller;

import lab.lp.api.model.User;
import lab.lp.api.model.Habit;
import lab.lp.api.service.HabitService;
import lab.lp.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/habitos")
public class HabitController {

    @Autowired
    private UserService userService;

    @Autowired
    private HabitService habitService;


}
