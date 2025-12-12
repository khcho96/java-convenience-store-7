package store.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Customer {

    private final Map<Product, List<Integer>> purchaseProducts;
    private int membershipDiscountAmount;

    private Customer() {
        purchaseProducts = new HashMap<>();
    }

    public static Customer newInstance() {
        return new Customer();
    }

    public void addProductForPromotion(Product product, int purchaseQuantity) {
        int buy = product.getPromotion().getBuy();
        int get = product.getPromotion().getGet();
        int setSize = buy + get;
        int setQuantity = purchaseQuantity / setSize;
        int setQuantityNotEqual = product.getPromotionQuantity() / setSize;
        if (setQuantity > setQuantityNotEqual) {
            setQuantity = setQuantityNotEqual;
        }
        int presentQuantity = get * setQuantity;
        int noPromotionQuantity = purchaseQuantity - setSize * setQuantity;

        purchaseProducts.put(product, List.of(purchaseQuantity, presentQuantity, noPromotionQuantity));
        product.updateStock(purchaseQuantity);
    }

    public void addProductForNormal(Product product, int purchaseQuantity) {
        purchaseProducts.put(product, List.of(purchaseQuantity, 0, purchaseQuantity));
        product.updateStock(purchaseQuantity);
    }

    public void setMembershipDiscountAmount() {
        membershipDiscountAmount = calculateMembershipDiscountAmount();
        if (membershipDiscountAmount > 8000) {
            membershipDiscountAmount = 8000;
        }
    }

    private int calculateMembershipDiscountAmount() {
        int sum = 0;
        for (Product product : purchaseProducts.keySet()) {
            int price = product.getPrice();
            int noPromotionQuantity = purchaseProducts.get(product).get(2);
            sum += price * noPromotionQuantity;
        }
        return (int) (sum * 0.3);
    }

    public Map<Product, List<Integer>> getPurchaseProducts() {
        return purchaseProducts;
    }

    public int getMembershipDiscountAmount() {
        return membershipDiscountAmount;
    }
}
