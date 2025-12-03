package lab.lp.api.service;


import lab.lp.api.dto.UserCreateDTO;
import lab.lp.api.dto.UserResponseDTO;
import lab.lp.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lab.lp.api.repository.UserRepository;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

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

    public User searchById (Long id) {
        return userRepository.findById(id);
    }

    public void deleteById (Long id) {
        userRepository.deleteById(id);
    }
}
