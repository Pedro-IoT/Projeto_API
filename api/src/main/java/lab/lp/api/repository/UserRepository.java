package lab.lp.api.repository;

import lab.lp.api.model.User;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Repository
public class UserRepository {

    private final Map<Long, User> usersMap = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public User save (User user) {
        if (user.getId() == null) {
            user.setId(idGenerator.incrementAndGet());
        }
        usersMap.put(user.getId(), user);
        return user;
    }

    public List<User> findAll() {
        return new ArrayList<>(usersMap.values());
    }

    public User findById (Long id) {
        return usersMap.get(id);
    }

    public void deleteById (Long id) {
        usersMap.remove(id);
    }

}
