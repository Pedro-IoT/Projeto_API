package lab.lp.api.repository;

import lab.lp.api.model.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HabitRepository extends JpaRepository<Habit, UUID> {
    List<Habit> findByUserId(UUID userId);
    Habit findHabitById(UUID id);
}
