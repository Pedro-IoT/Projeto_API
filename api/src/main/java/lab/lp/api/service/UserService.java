package lab.lp.api.service;


import lab.lp.api.dto.UserCreateDTO;
import lab.lp.api.dto.UserResponseDTO;
import lab.lp.api.model.User;
import org.springframework.stereotype.Service;
import lab.lp.api.repository.UserRepository;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {


    private final UserRepository userRepository;
    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserResponseDTO convertToDTO (User user) {
        return new UserResponseDTO(
                user.getName(),
                user.getId()
        );
    }

    public UserResponseDTO create (UserCreateDTO entryData) {
        User newUser = new User();
        newUser.setName(entryData.name());
        newUser.setEmail(entryData.email());
        userRepository.save(newUser);

        return convertToDTO(newUser);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User searchById (UUID id) {
        return userRepository.findUserById(id);
    }

    public void deleteById (UUID id) {
        userRepository.deleteById(id);
    }
}
