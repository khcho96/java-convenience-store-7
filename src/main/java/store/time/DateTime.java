package store.time;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;

public class DateTime {

    public static LocalDate now() {
        return DateTimes.now().toLocalDate();
    }
}
