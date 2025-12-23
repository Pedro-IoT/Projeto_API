package lab.lp.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lab.lp.api.dto.habit.HabitCreateDTO;
import lab.lp.api.dto.habit.HabitResponseDTO;
import lab.lp.api.domain.service.HabitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<HabitResponseDTO> createHabit (@RequestBody HabitCreateDTO body) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        HabitResponseDTO newHabit = habitService.create(body, userEmail);

        return ResponseEntity.status(HttpStatus.CREATED).body(newHabit);
    }

    @Operation(summary = "Realiza a listagem de hábitos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping
    public ResponseEntity<List<HabitResponseDTO>> listHabits () {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        List<HabitResponseDTO> list = habitService.habitsList(userEmail);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Deleta um hábito com base em seu id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Hábito deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Erro: Hábito não encontrado")
    })
    @DeleteMapping("/{habitId}")
    public ResponseEntity<Void> deleteHabit (@PathVariable UUID habitId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        boolean isDeleted = habitService.deleteHabit(userEmail, habitId);

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
    public ResponseEntity<HabitResponseDTO> markAsDone (@PathVariable UUID habitId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        HabitResponseDTO doneHabit = habitService.markAsDone(userEmail, habitId);

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
    public ResponseEntity<List<HabitResponseDTO>> listCheckedToday () {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        List<HabitResponseDTO> list = habitService.habitsCheckedToday(userEmail);

        return ResponseEntity.ok(list);
    }
}