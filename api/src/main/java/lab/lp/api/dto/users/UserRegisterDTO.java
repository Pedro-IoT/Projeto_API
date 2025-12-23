package lab.lp.api.dto.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterDTO(
        @Schema(description = "Nome do usuário a ser criado", example = "João")
        @NotBlank(message = "O nome é obrigatório")
        String name,

        @Schema(description = "Email do usuário a ser criado", example = "joao@gmail.com")
        @NotBlank() @Email(message = "E-mail inválido")
        String email,

        @Schema(description = "Senha do usuário a ser criada", example = "joao123")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        String password
        ) {

}
