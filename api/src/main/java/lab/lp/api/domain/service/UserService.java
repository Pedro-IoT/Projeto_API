package lab.lp.api.domain.service;


import jakarta.transaction.Transactional;
import lab.lp.api.domain.model.UserRole;
import lab.lp.api.dto.users.UserLoginDTO;
import lab.lp.api.dto.users.UserLoginResponseDTO;
import lab.lp.api.dto.users.UserRegisterDTO;
import lab.lp.api.dto.users.UserResponseDTO;
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

    private UserResponseDTO convertToDTO (User user) {
        return new UserResponseDTO(
                user.getName(),
                user.getId()
        );
    }

    @Transactional
    public UserResponseDTO registerUser (UserRegisterDTO data) {
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

        return convertToDTO(newUser);
    }

    public UserLoginResponseDTO login (UserLoginDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var userDetails = (CustomUserDetails) auth.getPrincipal();

        var token = tokenService.generateToken(userDetails.getUser());

        return new UserLoginResponseDTO(token);
    }
}
