package store.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Promotions {

    private final List<Promotion> promotions;

    private Promotions() {
        this.promotions = new ArrayList<>();
    }

    public static Promotions newInstance() {
        return new Promotions();
    }

    public void addPromotion(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {

    }
}
