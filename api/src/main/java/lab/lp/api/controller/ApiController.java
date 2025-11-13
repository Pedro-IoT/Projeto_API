package lab.lp.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@RestController
public class ApiController {
    private List<String> habits = new ArrayList<>();

    private ObjectMapper objectMapper;

    public ApiController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping(path = "/habits")
    public ResponseEntity<String> listTasks() throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper.writeValueAsString(habits));
    }

    @PostMapping(path = "/habits")
    public ResponseEntity<Void> createTask(@RequestBody String habit) {
        habits.add(habit);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/habits")
    public ResponseEntity<Void> clearTasks() {
        habits = new ArrayList<>();
        return ResponseEntity.ok().build();
    }
}
