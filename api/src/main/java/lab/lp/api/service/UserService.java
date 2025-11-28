package lab.lp.api.service;

import lab.lp.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lab.lp.api.repository.UserRepository;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User create (User user) {
        return userRepository.save(user);
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
