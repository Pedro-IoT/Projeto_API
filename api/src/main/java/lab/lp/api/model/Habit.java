package lab.lp.api.model;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Habit {

    private Long id;
    private String name;
    private Long userId;

    private List<LocalDate> dateChecks = new ArrayList<>();

    public Habit(){}

    public Habit(Long id, String name, Long userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setId(Long userId) {
        this.userId = userId;
    }

    public List<LocalDate> getDateChecks () {
        return dateChecks;
    }

    public void addDateCheck (LocalDate date) {
        this.dateChecks.add(date);
    }
}
