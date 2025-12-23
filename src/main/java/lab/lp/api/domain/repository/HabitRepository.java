package lab.lp.api.domain.repository;

import lab.lp.api.domain.model.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HabitRepository extends JpaRepository<Habit, UUID> {

    @Query("SELECT DISTINCT h FROM Habit h LEFT JOIN FETCH h.dateChecks WHERE h.user.id = :userId")
    List<Habit> findByUserId(@Param("userId") UUID userId);

    @Query("SELECT h FROM Habit h LEFT JOIN FETCH h.dateChecks WHERE h.id = :id AND h.user.id = :userId")
    Optional<Habit> findByIdAndUserID(@Param("id") UUID id, @Param("userId") UUID userId);
}
