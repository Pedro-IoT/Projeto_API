package lab.lp.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserCreateDTO(
        @Schema(description = "Nome do usuário a ser criado", example = "João")
        String name,

        @Schema(description = "Email do usuário a ser criado", example = "joao@gmail.com")
        String email) {
}
