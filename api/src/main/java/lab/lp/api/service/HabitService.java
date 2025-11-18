package lab.lp.api.service;

import lab.lp.api.model.Habit;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class HabitService {

    private final Map<Long, Habit> habitsMap = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    private Habit searchUserHabit (Long habitId, Long userId) {
        Habit habit = habitsMap.get(habitId);

        if (habit != null && habit.getId().equals(userId)) {
            return habit;
        }
        return null;
    }

    public Habit create (Habit newHabit, Long userId) {
        Long id = idGenerator.incrementAndGet();
        newHabit.setId(id);
        newHabit.setUserId(userId);
        habitsMap.put(id, newHabit);

        return newHabit;
    }

    public List<Habit> habitsList (Long userId) {
        List<Habit> userHabitsList = new ArrayList<>();

        for (Habit h : habitsMap.values()) {

            if (h.getUserId().equals(userId)) {
                userHabitsList.add(h);
            }
        }
        return userHabitsList;
    }

    public boolean deleteHabit (Long userId, Long habitId) {
        Habit habit = searchUserHabit(userId, habitId);

        if (habit != null){
            habitsMap.remove(habitId);
            return true;
        }
        return false;
    }

    public Habit markAsDone (Long userId, Long habitId) {
        Habit habit = searchUserHabit(userId, habitId);

        if (habit != null) {
            LocalDate today = LocalDate.now();
            if (!habit.getDateChecks().contains(today)){
                habit.addDateCheck(today);
            }
        }
        return habit;
    }

}
