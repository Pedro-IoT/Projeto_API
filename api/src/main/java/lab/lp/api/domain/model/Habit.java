package lab.lp.api.domain.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;


@Entity
@Table(name = "TB_HABITS")
public class Habit implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ElementCollection
    @CollectionTable(
            name = "TB_HABIT_CHECKS",
            joinColumns = @JoinColumn(name = "habit_id")
    )
    @Column(name = "CHECK_DATE")
    private final List<LocalDate> dateChecks = new ArrayList<>();

    public Habit(){}

    public UUID getId() {
        return id;
    }

    public void setId(UUID habitId) {
        this.id = habitId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser () {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<LocalDate> getDateChecks () {
        return dateChecks;
    }

    public void addDateCheck (LocalDate date) {
        this.dateChecks.add(date);
    }

    public boolean checkedToday () {
        LocalDate today = LocalDate.now();
        return this.dateChecks.contains(today);
    }

    public int getCurrentStreak (LocalDate today) {
        if (this.dateChecks.isEmpty()) {
            return 0;
        }
        List<LocalDate> copyDates = new ArrayList<>(this.dateChecks);
        copyDates.sort(Collections.reverseOrder());

        int streak = 0;
        LocalDate yesterday = today.minusDays(1);

        LocalDate lastChecked = copyDates.get(0);
        if (!lastChecked.isEqual(today) && !lastChecked.isEqual(yesterday)) {
            return 0;
        }

        LocalDate expectedDate = lastChecked;

        for (LocalDate date : copyDates) {
            if (date.isEqual(expectedDate)){
                streak++;
                expectedDate = date.minusDays(1);
            }
            else {
                break;
            }
        }
        return streak;
    }
}


