package lab.lp.api.dto.habit;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record HabitResponseDTO(
        @Schema(description = "Identificador único do hábito", example = "d3ee2929-212b-4077-af84-694a0e69b8e1")
        UUID id,

        @Schema(description = "Nome/descrição do hábito", example = "Ler 10 páginas")
        String name,

        @Schema(description = "Sequência de dias que o hábito foi feito", example = "1")
        int sequenceOfDays,

        @Schema(description = "Se o hábito foi marcado como feito hoje", example = "true")
        boolean checkedToday) {
}
