package store.domain;

import java.util.HashMap;
import java.util.Map;

public class Result {

    private Map<Item, Integer> presentItems = new HashMap<>();
    private Map<Item, Integer> finalPurchaseItems = new HashMap<>();
    private int noPromotionPrice = 0;
    private int membershipDiscountPrice;

    public void addPresentItem(Item item, int quantity) {
        presentItems.put(item, quantity);
    }

    public void addPurchaseItem(Item item, int quantity) {
        finalPurchaseItems.put(item, quantity);
    }

    public void addNoPromotionPrice(int price) {
        noPromotionPrice += price;
    }

    public void addMembershipDiscount() {
        int price = noPromotionPrice * 3 / 10;
        this.membershipDiscountPrice =  Math.min(price, 8000);
    }

    public Map<Item, Integer> getFinalPurchaseItems() {
        return finalPurchaseItems;
    }

    public int getMembershipDiscountPrice() {
        return membershipDiscountPrice;
    }

    public Map<Item, Integer> getPresentItems() {
        return presentItems;
    }

    public int getTotalPrice() {
        int totalPrice = 0;
        for (Item item : finalPurchaseItems.keySet()) {
            totalPrice += item.getPrice() * finalPurchaseItems.get(item);
        }
        return totalPrice;
    }

    public int getPromotionPrice() {
        int totalPrice = 0;
        for (Item item : presentItems.keySet()) {
            totalPrice += item.getPrice() * presentItems.get(item);
        }
        return totalPrice;
    }

    public int getFinalPrice() {
        return getTotalPrice() - getPromotionPrice() - membershipDiscountPrice;
    }

    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (Item item : finalPurchaseItems.keySet()) {
            totalQuantity += finalPurchaseItems.get(item);
        }
        return totalQuantity;
    }
}
