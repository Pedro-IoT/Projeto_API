package lab.lp.api.controller;

import lab.lp.api.dto.HabitCreateDTO;
import lab.lp.api.dto.HabitResponseDTO;
import lab.lp.api.model.Habit;
import lab.lp.api.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("users/{userId}/habits")
@RestController
public class HabitController {

    @Autowired
    private HabitService habitService;

    @PostMapping
    public ResponseEntity<HabitResponseDTO> createHabit (@PathVariable Long userId, @RequestBody HabitCreateDTO body) {
        HabitResponseDTO newHabit = habitService.create(body, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(newHabit);
    }

    @GetMapping
    public ResponseEntity<List<HabitResponseDTO>> listHabits (@PathVariable Long userId) {
        List<HabitResponseDTO> list = habitService.habitsList(userId);
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{habitId}")
    public ResponseEntity<Void> deleteHabit (@PathVariable Long userId, @PathVariable Long habitId) {
        boolean isDeleted = habitService.deleteHabit(userId, habitId);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{habitId}")
    public ResponseEntity<HabitResponseDTO> markAsDone (@PathVariable Long userId, @PathVariable Long habitId) {
        HabitResponseDTO doneHabit = habitService.markAsDone(userId, habitId);

        if (doneHabit != null) {
            return ResponseEntity.ok(doneHabit);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/doneToday")
    public ResponseEntity<List<HabitResponseDTO>> listCheckedToday (@PathVariable Long userId) {
        List<HabitResponseDTO> list = habitService.habitsCheckedToday(userId);

        return ResponseEntity.ok(list);
    }
}
