package store.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Stock {

    private final List<Item> items;

    public Stock() {
        this.items = new ArrayList<>();
    }

    public static Stock newInstance() {
        return new Stock();
    }

    public void addItem(String itemName, int price, int quantity, Promotion promotion) {
        Optional<Item> item = items.stream()
                .filter(it -> it.getName().equals(itemName))
                .findFirst();

        if (item.isPresent()) {
            item.get().setNormalQuantity(quantity);
            return;
        }

        items.add(Item.of(itemName, price, quantity, promotion));
    }

    public List<Item> getItems() {
        return items;
    }

    public void update(Result result) {
        Map<Item, Integer> finalPurchaseItems = result.getFinalPurchaseItems();
        for (Item item : finalPurchaseItems.keySet()) {
            item.update(finalPurchaseItems.get(item));
        }
    }
}
