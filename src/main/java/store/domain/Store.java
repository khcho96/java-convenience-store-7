package store.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import store.constant.ErrorMessage;
import store.constant.Option;
import store.util.InputParser;
import store.util.Retry;
import store.view.InputView;

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

    public Result purchase(Map<String, Integer> purchaseItems) {
        List<Item> items = stock.getItems();
        validate(items, purchaseItems);

        Result result = new Result();

        for (String itemName : purchaseItems.keySet()) {
            Item item = getItem(items, itemName);
            int purchaseQuantity = purchaseItems.get(itemName);

            if (item.isPromotionImpossible()) {
                result.addNoPromotionPrice(item.getPrice() * purchaseQuantity);
                result.addPurchaseItem(item, purchaseQuantity);
                continue;
            }

            // 구매 수량 > 프로모션 수량 인지 확인
            if (item.isPromotionQuantityShortage(purchaseQuantity)) {
                // 맞으면 몇개를 정가로 사야하는지 계산 후 알려주고 입력 받아야함
                int promotionQuantityShortage = item.getPromotionQuantityShortage(purchaseQuantity);

                Option option = Retry.retryUntilSuccess(() ->
                        InputParser.parseOption(InputView.readPromotionQuantityShortage(itemName, promotionQuantityShortage))
                );
                int promotionQuantity = purchaseQuantity - promotionQuantityShortage;
                // Y: 일부는 할인적용, 나머지는 정가 계산, continue
                if (option.equals(Option.YES)) {
                    result.addPurchaseItem(item, purchaseQuantity);
                    if (item.getPromotion().getPresentQuantity(promotionQuantity) != 0){
                        result.addPresentItem(item, item.getPromotion().getPresentQuantity(promotionQuantity));
                    }
                    result.addNoPromotionPrice(item.getPrice() * promotionQuantityShortage);
                    continue;
                }

                // N: 정가 수량 제외 후 결제, continue
                result.addPurchaseItem(item, promotionQuantity);
                result.addPresentItem(item, item.getPromotion().getPresentQuantity(promotionQuantity));
                continue;
            }

            if (item.isChanceOfFree(purchaseQuantity) && item.isMoreThan(purchaseQuantity)) {
                Option option = Retry.retryUntilSuccess(() ->
                        InputParser.parseOption(InputView.readChanceOfFree(itemName))
                );
                if (option.equals(Option.YES)) {
                    result.addPurchaseItem(item, purchaseQuantity + 1);
                    result.addPresentItem(item, item.getPromotion().getPresentQuantity(purchaseQuantity + 1));
                    continue;
                }
            }

            result.addPurchaseItem(item, purchaseQuantity);
            result.addPresentItem(item, item.getPromotion().getPresentQuantity(purchaseQuantity));
            result.addNoPromotionPrice(item.getPrice() * item.getPromotion().getNoPresentQuantity(purchaseQuantity));
        }

        Option option = Retry.retryUntilSuccess(() ->
            InputParser.parseOption(InputView.readMembership())
        );
        if (option.equals(Option.YES)) {
            result.addMembershipDiscount();
        }

        stock.update(result);

        return result;
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
