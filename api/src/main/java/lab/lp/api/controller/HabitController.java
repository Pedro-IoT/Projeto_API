package lab.lp.api.controller;

import lab.lp.api.model.Habit;
import lab.lp.api.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("users/{userId}/habits")
@RestController
public class HabitController {

    @Autowired
    private HabitService habitService;

    @PostMapping
    public Habit createHabit (@PathVariable Long userId, @RequestBody Habit habit) {
        return habitService.create(habit, userId);
    }

    @GetMapping
    public List<Habit> listHabits (@PathVariable Long userId) {
        return habitService.habitsList(userId);
    }

    @DeleteMapping("/{habitId}")
    public boolean deleteHabit (@PathVariable Long userId, @PathVariable Long habitId) {
        return habitService.deleteHabit(userId, habitId);
    }

    @PatchMapping("/{habitId}")
    public Habit markAsDone (@PathVariable Long userId, @PathVariable Long habitId) {
        return habitService.markAsDone(userId, habitId);
    }
}
