package lab.lp.api.repository;

import lab.lp.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findUserByName(String name);
    User findUserByEmail(String email);
    User findUserById(UUID id);
}