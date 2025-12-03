package lab.lp.api.service.logic;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class StreakCalculator {

   public int calculate(List<LocalDate> dateChecks) {

       if (dateChecks == null || dateChecks.isEmpty()) {
           return 0;
       }
       List<LocalDate> copyDates = new ArrayList<>(dateChecks);
       copyDates.sort(Collections.reverseOrder());

       int streak = 0;
       LocalDate today = LocalDate.now();
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
