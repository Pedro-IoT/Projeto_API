package lab.lp.api.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserLoginResponseDTO(
        @Schema(description = "Token JWT do Usuário")
        String token
    ) {
}
