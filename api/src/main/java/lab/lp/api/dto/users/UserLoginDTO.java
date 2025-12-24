package lab.lp.api.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserLoginDTO(
        @Schema(description = "Email do Usuário", example = "joao@gmail.com")
        String email,

        @Schema(description = "Senha do Usuário", example = "teste1234")
        String password
) {
}
