package store.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import store.domain.Stock;
import store.domain.Store;

public class StoreService {

    private final Store store;

    public StoreService() {
        this.store = Store.newInstance();
    }

    public void registerPromotions(List<String> readLines) {
        readLines.removeFirst();

        for (String readLine : readLines) {
            String[] promotionInfo = readLine.split(",");
            int buy = Integer.parseInt(promotionInfo[1]);
            int get = Integer.parseInt(promotionInfo[2]);
            LocalDate startDate = LocalDate.parse(promotionInfo[3]);
            LocalDate endDate = LocalDate.parse(promotionInfo[4]);

            store.addPromotion(promotionInfo[0], buy, get, startDate, endDate);
        }
    }

    public void registerProducts(List<String> readLines) {
        readLines.removeFirst();

        for (String readLine : readLines) {
            String[] itemInfo = readLine.split(",");

            store.addItem(itemInfo[0], Integer.parseInt(itemInfo[1]), Integer.parseInt(itemInfo[2]), itemInfo[3]);
        }
    }

    public Stock getStock() {
        return store.getStock();
    }

    public void purchase(Map<String, Integer> items) {
        store.purchase(items);
    }
}
