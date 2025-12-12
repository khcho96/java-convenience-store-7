package store.domain;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.dto.ReceiptDto;

public class Customer {

    private final Map<Product, List<Integer>> purchaseProducts;
    private int membershipDiscountAmount;

    private Customer() {
        purchaseProducts = new LinkedHashMap<>();
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

    public ReceiptDto getReceipt() {
        Map<Product, Integer> purchaseProducts = new LinkedHashMap<>();
        Map<Product, Integer> presentProducts = new LinkedHashMap<>();
        for (Product product : this.purchaseProducts.keySet()) {
            List<Integer> values = this.purchaseProducts.get(product);

            int purchaseQuantity = values.get(0);
            purchaseProducts.put(product, purchaseQuantity);

            int presentQuantity = values.get(1);
            if (presentQuantity > 0) {
                presentProducts.put(product, presentQuantity);
            }
        }
        return new ReceiptDto(purchaseProducts, presentProducts, membershipDiscountAmount);
    }
}
