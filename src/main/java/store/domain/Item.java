package store.domain;

public class Item {

    private final String name;
    private final int price;
    private int proQuantity;
    private int quantity;
    private final Promotion promotion;

    private Item(String name, int price, int proQuantity, int quantity, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.proQuantity = proQuantity;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public static Item of(String name, int price, int quantity, Promotion promotion) {
        if (promotion != null) {
            return new Item(name, price, quantity, 0, promotion);
        }
        return new Item(name, price, 0, quantity, null);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getProQuantity() {
        return proQuantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setNormalQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalQuantity() {
        return proQuantity + quantity;
    }

    public boolean isPromotionImpossible() {
        return promotion == null || promotion.isImpossible();
    }

    public boolean isPromotionQuantityShortage(int purchaseQuantity) {
        return (purchaseQuantity > proQuantity) ||
                (purchaseQuantity == proQuantity && isChanceOfFree(purchaseQuantity));
    }

    public boolean isChanceOfFree(int purchaseQuantity) {
        return purchaseQuantity % (promotion.getBuy() + promotion.getGet()) == promotion.getBuy();
    }

    public int getPromotionQuantityShortage(int purchaseQuantity) {
        return purchaseQuantity - (proQuantity / (promotion.getBuy() + promotion.getGet())) * (promotion.getBuy()
                + promotion.getGet());
    }

    public boolean isMoreThan(int purchaseQuantity) {
        return proQuantity > purchaseQuantity;
    }

    public void update(int purchaseQuantity) {
        if (proQuantity != 0) {
            if (proQuantity >= purchaseQuantity) {
                proQuantity -= purchaseQuantity;
                return;
            }
            purchaseQuantity -= proQuantity;
            proQuantity = 0;
        }

        quantity -= purchaseQuantity;
    }
}
