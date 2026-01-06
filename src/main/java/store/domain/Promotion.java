package store.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;

public class Promotion {

    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Promotion of(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        return new Promotion(name, buy, get, startDate, endDate);
    }

    public String getName() {
        return name;
    }

    public boolean isImpossible() {
        LocalDate now = DateTimes.now().toLocalDate();
        return now.isBefore(startDate) || now.isAfter(endDate);
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    public int getPresentQuantity(int promotionQuantity) {
        return promotionQuantity / (buy + get);
    }

    public int getNoPresentQuantity(int purchaseQuantity) {
        return purchaseQuantity % (buy + get);
    }
}
