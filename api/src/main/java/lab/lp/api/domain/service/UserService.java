package lab.lp.api.domain.service;


import jakarta.transaction.Transactional;
import lab.lp.api.domain.model.UserRole;
import lab.lp.api.dto.users.UserLoginDTO;
import lab.lp.api.dto.users.UserLoginResponseDTO;
import lab.lp.api.dto.users.UserRegisterDTO;
import lab.lp.api.domain.model.User;
import lab.lp.api.infra.exception.HabitTrackerException;
import lab.lp.api.infra.security.CustomUserDetails;
import lab.lp.api.infra.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lab.lp.api.domain.repository.UserRepository;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;


    public UserService (UserRepository userRepository, PasswordEncoder passwordEncoder,
                        AuthenticationManager authenticationManager,
                        TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    private UserLoginResponseDTO convertToDTO (User user, String token) {
        return new UserLoginResponseDTO(
                token,
                user.getName()
        );
    }
    private String generateToken (String email, String password) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(email, password);
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var userDetails = (CustomUserDetails) auth.getPrincipal();

        return tokenService.generateToken(userDetails.getUser());
    }

    @Transactional
    public UserLoginResponseDTO registerUser (UserRegisterDTO data) {
        if (userRepository.findUserByEmail(data.email()).isPresent()) {
            throw new HabitTrackerException("E-mail já cadastrado");
        }

        String encryptedPassword = passwordEncoder.encode(data.password());

        User newUser = new User();
        newUser.setName(data.name());
        newUser.setEmail(data.email());
        newUser.setRole(UserRole.USER);
        newUser.setPassword(encryptedPassword);
        userRepository.save(newUser);

        return convertToDTO(newUser, generateToken(data.email(), data.password()));
    }

    public UserLoginResponseDTO login (UserLoginDTO data) {


        User user = userRepository.findUserByEmail(data.email()).orElse(null);
        if (user == null) {
            throw new HabitTrackerException("Usuário não econtrado!");
        }

        return new UserLoginResponseDTO(generateToken(data.email(), data.password()), user.getName());
    }
}
