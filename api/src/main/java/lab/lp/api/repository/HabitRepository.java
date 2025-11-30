package lab.lp.api.repository;

import lab.lp.api.model.Habit;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Repository
public class HabitRepository {

    private final Map<Long, Habit> habitsMap = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public Habit save (Habit habit) {
        habit.setId(idGenerator.incrementAndGet());
        habitsMap.put(habit.getId(), habit);
        return habit;
    }

    public List<Habit> findAll() {
        return new ArrayList<>(habitsMap.values());
    }

    public Habit findById (Long id) {
        return habitsMap.get(id);
    }

    public void deleteById (Long id) {
        habitsMap.remove(id);
    }

}
