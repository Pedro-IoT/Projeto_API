package lab.lp.api.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record UserResponseDTO(
        @Schema(description = "Nome do usuário que foi criado", example = "João")
        String name,

        @Schema(description = "Identificador único do usuário", example = "d3ee2929-212b-4077-af84-694a0e69b8e1")
        UUID id) {
}
