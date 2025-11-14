package lab.lp.api.service;

import lab.lp.api.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final Map<Long, User> usersMap = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public User create (User user) {
        long id = idGenerator.incrementAndGet();
        user.setId(id);
        usersMap.put(id, user);

        return user;
    }

    public User searchById (Long id) {
        return usersMap.get(id);
    }
}
