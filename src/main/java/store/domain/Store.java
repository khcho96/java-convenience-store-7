package store.domain;

import java.time.LocalDate;

public class Store {

    private final Stock stock;
    private final Promotions promotions;

    private Store() {
        this.stock = Stock.newInstance();
        this.promotions = Promotions.newInstance();
    }

    public static Store newInstance() {
        return new Store();
    }

    public void addItem(String itemName, int price, int quantity, String promotionName) {
        stock.addItem(itemName, price, quantity, promotions.getPromotion(promotionName));
    }

    public void addPromotion(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        promotions.addPromotion(name, buy, get, startDate, endDate);
    }

    public Stock getStock() {
        return stock;
    }
}
