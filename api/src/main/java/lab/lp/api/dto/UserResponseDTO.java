package lab.lp.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserResponseDTO(
        @Schema(description = "Nome do usuário que foi criado", example = "João")
        String name,

        @Schema(description = "Identificador único do usuário", example = "Ler 10 páginas")
        Long id) {
}
