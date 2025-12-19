package lab.lp.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lab.lp.api.dto.HabitCreateDTO;
import lab.lp.api.dto.HabitResponseDTO;
import lab.lp.api.service.HabitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "habit-tracker")
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @Operation(summary = "Realiza a criação de hábitos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Hábito criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PostMapping
    public ResponseEntity<HabitResponseDTO> createHabit (@RequestParam String email, @RequestBody HabitCreateDTO body) {
        HabitResponseDTO newHabit = habitService.create(body, email);

        return ResponseEntity.status(HttpStatus.CREATED).body(newHabit);
    }

    @Operation(summary = "Realiza a listagem de hábitos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping
    public ResponseEntity<List<HabitResponseDTO>> listHabits (@RequestParam String email) {
        List<HabitResponseDTO> list = habitService.habitsList(email);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Deleta um hábito com base em seu id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Hábito deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Erro: Hábito não encontrado")
    })
    @DeleteMapping("/{habitId}")
    public ResponseEntity<Void> deleteHabit (@RequestParam String email, @PathVariable UUID habitId) {
        boolean isDeleted = habitService.deleteHabit(email, habitId);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Marca um hábito como realizado hoje")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hábito atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Hábito não encontrado")
    })
    @PatchMapping("/{habitId}")
    public ResponseEntity<HabitResponseDTO> markAsDone (@RequestParam String email, @PathVariable UUID habitId) {
        HabitResponseDTO doneHabit = habitService.markAsDone(email, habitId);

        if (doneHabit != null) {
            return ResponseEntity.ok(doneHabit);
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Mostra os hábitos realizados hoje")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/doneToday")
    public ResponseEntity<List<HabitResponseDTO>> listCheckedToday (@RequestParam String email) {
        List<HabitResponseDTO> list = habitService.habitsCheckedToday(email);

        return ResponseEntity.ok(list);
    }
}