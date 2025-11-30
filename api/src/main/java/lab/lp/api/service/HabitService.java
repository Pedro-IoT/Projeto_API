package lab.lp.api.service;

import lab.lp.api.model.Habit;
import lab.lp.api.repository.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HabitService {
    
    @Autowired
    HabitRepository habitRepository;

    private Habit searchUserHabit (Long userId, Long habitId) {
        List <Habit> habitsList = habitRepository.findAll();

        for (Habit h : habitsList) {
            if(h.getUserId().equals(userId) && h.getId().equals(habitId)){
                return h;
            }
        }
        return null;
    }
    
    public Habit create (Habit newHabit, Long userId) {
        newHabit.setUserId(userId);
        
        return habitRepository.save(newHabit);
    }

    public List<Habit> habitsList (Long userId) {
        List <Habit> habitsList = habitRepository.findAll();
        List<Habit> userHabitsList = new ArrayList<>();

        for (Habit h : habitsList) {

            if (h.getUserId().equals(userId)) {
                userHabitsList.add(h);
            }
        }
        return userHabitsList;
    }

    public boolean deleteHabit (Long userId, Long habitId) {
        Habit habit = searchUserHabit(userId, habitId);

        if (habit != null){
            habitRepository.deleteById(habitId);
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
