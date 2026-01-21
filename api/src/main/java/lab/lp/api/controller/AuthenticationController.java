package lab.lp.api.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lab.lp.api.dto.users.UserLoginDTO;
import lab.lp.api.dto.users.UserLoginResponseDTO;
import lab.lp.api.dto.users.UserRegisterDTO;
import lab.lp.api.domain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthenticationController {
    private final UserService userService;


    public AuthenticationController (UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Realiza o Login de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário Logado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login (@RequestBody @Valid UserLoginDTO data) {
        UserLoginResponseDTO token = userService.login(data);

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }


    @Operation(summary = "Realiza a criação de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Não foi possível criar usuário")
    })
    @PostMapping("/register")
    public ResponseEntity<UserLoginResponseDTO> register(@RequestBody @Valid UserRegisterDTO data) {
         UserLoginResponseDTO newUser = userService.registerUser(data);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}
