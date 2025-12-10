package store.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Promotion {

    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private Promotion(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Promotion of(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        return new Promotion(name, buy, get, startDate, endDate);
    }

    public boolean equalsName(String name) {
        return this.name.equals(name);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Promotion promotion = (Promotion) object;
        return Objects.equals(name, promotion.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
