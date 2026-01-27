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
import lab.lp.api.infra.security.CookieService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthenticationController {
    private final UserService userService;
    private final CookieService cookieService;


    public AuthenticationController (UserService userService, CookieService cookieService) {
        this.userService = userService;
        this.cookieService = cookieService;
    }

    @Operation(summary = "Realiza o Login de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário Logado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login (@RequestBody @Valid UserLoginDTO data) {
        UserLoginResponseDTO responseDTO = userService.login(data);
        ResponseCookie cookie = cookieService.generateTokenCookie(responseDTO.token());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new UserLoginResponseDTO(null, responseDTO.name()));
    }


    @Operation(summary = "Realiza a criação de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Não foi possível criar usuário")
    })
    @PostMapping("/register")
    public ResponseEntity<UserLoginResponseDTO> register(@RequestBody @Valid UserRegisterDTO data) {
         UserLoginResponseDTO responseDTO = userService.registerUser(data);

         ResponseCookie cookie = cookieService.generateTokenCookie(responseDTO.token());

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new UserLoginResponseDTO(null, responseDTO.name()));
    }

    @Operation(summary = "Realiza o Logout de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deslogado com sucesso")
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie cookie = cookieService.getCleanTokenCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
    @Operation(summary = "Retorna os dados do usuário logado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados do usuário retornados com sucesso"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado")
    })
    @GetMapping("/me")
    public ResponseEntity<String> me() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        return (ResponseEntity.ok(userEmail));
    }
}
