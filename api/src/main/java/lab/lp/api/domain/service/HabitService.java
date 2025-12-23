package lab.lp.api.domain.service;

import jakarta.transaction.Transactional;
import lab.lp.api.dto.habit.HabitCreateDTO;
import lab.lp.api.dto.habit.HabitResponseDTO;
import lab.lp.api.domain.model.Habit;
import lab.lp.api.domain.model.User;
import lab.lp.api.domain.repository.HabitRepository;
import lab.lp.api.domain.repository.UserRepository;
import lab.lp.api.infra.exception.HabitTrackerException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class HabitService {

    private final HabitRepository habitRepository;

    private final UserRepository userRepository;

    public HabitService (HabitRepository habitRepository, UserRepository userRepository) {
        this.habitRepository = habitRepository;
        this.userRepository = userRepository;
    }

    private HabitResponseDTO convertToDTO (Habit habit) {
        return new HabitResponseDTO(
                habit.getId(),
                habit.getName(),
                habit.getCurrentStreak(LocalDate.now()),
                habit.checkedToday()
        );
    }

    private Habit searchUserHabit (User user, UUID habitId) {
        Habit habit = habitRepository.findByIdAndUserID(habitId, user.getId()).orElse(null);

        if (habit != null && habit.getUser().getId().equals(user.getId())) {
            return habit;
        }
        return null;
    }

    private User findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email).orElse(null);
        if (user == null) throw new HabitTrackerException("Usuário não encontrado!");
        return user;
    }

    @Transactional
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

    @Transactional
    public boolean deleteHabit (String email, UUID habitId) {
        User user = findUserByEmail(email);
        Habit habit = searchUserHabit(user, habitId);

        if (habit != null){
            habitRepository.delete(habit);
            return true;
        }
        return false;
    }

    @Transactional
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