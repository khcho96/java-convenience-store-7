package store.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import store.constant.ErrorMessage;

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

    public void purchase(Map<String, Integer> purchaseItems) {
        List<Item> items = stock.getItems();

        validate(items, purchaseItems);


    }

    private void validate(List<Item> items, Map<String, Integer> purchaseItems) {
        for (String itemName : purchaseItems.keySet()) {
            Item item = getItem(items, itemName);
            validatePurchasePossible(item, purchaseItems.get(itemName));
        }
    }

    private Item getItem(List<Item> items, String itemName) {
        return items.stream()
                .filter(item -> item.getName().equals(itemName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.NO_EXIST_ITEMS_ERROR.getErrorMessage()));
    }

    private void validatePurchasePossible(Item item, int purchaseQuantity) {
        if (purchaseQuantity > item.getTotalQuantity()) {
            throw new IllegalArgumentException(ErrorMessage.MAX_ITEMS_ERROR.getErrorMessage());
        }
    }
}
