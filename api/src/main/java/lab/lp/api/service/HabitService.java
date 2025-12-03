package lab.lp.api.service;

import lab.lp.api.dto.HabitCreateDTO;
import lab.lp.api.dto.HabitResponseDTO;
import lab.lp.api.model.Habit;
import lab.lp.api.repository.HabitRepository;
import lab.lp.api.service.logic.StreakCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HabitService {
    
    @Autowired
    HabitRepository habitRepository;

    @Autowired
    StreakCalculator streakCalculator;

    private HabitResponseDTO convertToDTO (Habit habit) {
        int sequenceOfDays = streakCalculator.calculate(habit.getDateChecks());
        return new HabitResponseDTO(
                habit.getId(),
                habit.getName(),
                sequenceOfDays
        );
    }

    private Habit searchUserHabit (Long userId, Long habitId) {
        Habit habit = habitRepository.findById(habitId);

        if (habit != null && habit.getUserId().equals(userId)) {
            return habit;
        }
        return null;
    }
    
    public HabitResponseDTO create (HabitCreateDTO entryData, Long userId) {
        Habit newHabit = new Habit();
        newHabit.setName(entryData.name());
        newHabit.setUserId(userId);
        Habit savedHabit = habitRepository.save(newHabit);
        
        return convertToDTO(savedHabit);
    }

    public List<HabitResponseDTO> habitsList (Long userId) {
        List <Habit> habitsList = habitRepository.findAll();
        List<HabitResponseDTO> userHabitsList = new ArrayList<>();

        for (Habit h : habitsList) {

            if (h.getUserId().equals(userId)) {
                HabitResponseDTO convertedH = convertToDTO(h);
                userHabitsList.add(convertedH);
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

    public HabitResponseDTO markAsDone (Long userId, Long habitId) {
        Habit habit = searchUserHabit(userId, habitId);

        if (habit != null) {
            LocalDate today = LocalDate.now();
            if (!habit.getDateChecks().contains(today)){
                habit.addDateCheck(today);
            }
        }

        return convertToDTO(habit);
    }
}
