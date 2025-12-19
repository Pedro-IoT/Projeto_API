package lab.lp.api.service;

import lab.lp.api.dto.HabitCreateDTO;
import lab.lp.api.dto.HabitResponseDTO;
import lab.lp.api.model.Habit;
import lab.lp.api.model.User;
import lab.lp.api.repository.HabitRepository;
import lab.lp.api.repository.UserRepository;
import lab.lp.api.service.logic.StreakCalculator;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class HabitService {

    private final HabitRepository habitRepository;

    private final StreakCalculator streakCalculator;

    private final UserRepository userRepository;

    public HabitService (HabitRepository habitRepository, StreakCalculator streakCalculator, UserRepository userRepository) {
        this.habitRepository = habitRepository;
        this.streakCalculator = streakCalculator;
        this.userRepository = userRepository;
    }

    private HabitResponseDTO convertToDTO (Habit habit) {
        int sequenceOfDays = streakCalculator.calculate(habit.getDateChecks());
        return new HabitResponseDTO(
                habit.getId(),
                habit.getName(),
                sequenceOfDays,
                checkedToday(habit.getDateChecks())
        );
    }

    private Habit searchUserHabit (User user, UUID habitId) {
        Habit habit = habitRepository.findById(habitId).orElse(null);

        if (habit != null && habit.getUser().getId().equals(user.getId())) {
            return habit;
        }
        return null;
    }

    private User findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) throw new RuntimeException("Usuário não encontrado!");
        return user;
    }
    
    public HabitResponseDTO create (HabitCreateDTO entryData, String email) {
        User user = findUserByEmail(email);
        Habit newHabit = new Habit();
        newHabit.setName(entryData.name());
        newHabit.setUser(user);
        Habit savedHabit = habitRepository.save(newHabit);
        
        return convertToDTO(savedHabit);
    }

    public List<HabitResponseDTO> habitsList (String email) {
        User user = findUserByEmail(email);
        List <Habit> habitsList = habitRepository.findByUserId(user.getId());
        List<HabitResponseDTO> userHabitsList = new ArrayList<>();

        for (Habit habit : habitsList) {
            HabitResponseDTO convertedHabit = convertToDTO(habit);
            userHabitsList.add(convertedHabit);
        }
        return userHabitsList;
    }

    public boolean deleteHabit (String email, UUID habitId) {
        User user = findUserByEmail(email);
        Habit habit = searchUserHabit(user, habitId);

        if (habit != null){
            habitRepository.delete(habit);
            return true;
        }
        return false;
    }

    public HabitResponseDTO markAsDone (String email, UUID habitId) {
        User user = findUserByEmail(email);
        Habit habit = searchUserHabit(user, habitId);

        if (habit != null) {
            LocalDate today = LocalDate.now();
            if (!habit.getDateChecks().contains(today)){
                habit.addDateCheck(today);
                habitRepository.save(habit);
            }
        }
        else return null;

        return convertToDTO(habit);
    }

    private boolean checkedToday (List<LocalDate> dateChecks) {
        LocalDate today = LocalDate.now();
        return dateChecks.contains(today);
    }

    public List<HabitResponseDTO> habitsCheckedToday (String email) {
        List <HabitResponseDTO> habitsList = habitsList(email);
        List<HabitResponseDTO> habitsCheckedToday = new ArrayList<>();

        for (HabitResponseDTO habit : habitsList) {
            if (habit.checkedToday()) {
                habitsCheckedToday.add(habit);
            }
        }

        return habitsCheckedToday;
    }
}