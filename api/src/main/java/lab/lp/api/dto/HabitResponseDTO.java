package lab.lp.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record HabitResponseDTO(
        @Schema(description = "Identificador único do hábito", example = "1")
        Long id,

        @Schema(description = "Nome/descrição do hábito", example = "Ler 10 páginas")
        String name,

        @Schema(description = "Sequência de dias que o hábito foi feito", example = "1")
        int sequenceOfDays,

        @Schema(description = "Se o hábito foi marcado como feito hoje", example = "true")
        boolean checkedToday) {
}
