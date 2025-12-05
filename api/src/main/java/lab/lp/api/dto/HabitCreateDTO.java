package lab.lp.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record HabitCreateDTO(
        @Schema(description = "Nome do hábito a ser criado", example = "Estudar FMC")
        String name) {
}
