package store.view;

import static java.util.Locale.KOREA;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import store.domain.Item;
import store.domain.Promotion;
import store.domain.Result;
import store.domain.Stock;

public class OutputView {

    private static final DateTimeFormatter DATETIME_FMT =
            DateTimeFormatter.ofPattern("M월 dd일 E요일 HH:mm", KOREA);
    private static final DateTimeFormatter DATE_FMT =
            DateTimeFormatter.ofPattern("M월 dd일 E요일", KOREA);
    private static final DateTimeFormatter TIME_FMT =
            DateTimeFormatter.ofPattern("HH:mm", KOREA);

    private OutputView() {
    }

    public static void printErrorMessage(IllegalArgumentException e) {
        System.out.println(e.getMessage());
    }

    public static void printStock(Stock stock) {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.\n");

        List<Item> items = stock.getItems();
        for (Item item : items) {
            String name = item.getName();
            int price = item.getPrice();
            int proQuantity = item.getProQuantity();
            int quantity = item.getQuantity();
            Promotion promotion = item.getPromotion();

            if (promotion != null) {
                if (proQuantity == 0) {
                    System.out.printf("- %s %,d원 재고 없음 %s\n%n", name, price, promotion.getName());
                    System.out.printf("- %s %,d원 재고 없음%n\n", name, price);
                    continue;
                }

                if (quantity == 0) {
                    System.out.printf("- %s %,d원 %d개 %s\n", name, price, proQuantity, promotion.getName());
                    System.out.printf("- %s %,d원 재고 없음\n", name, price);
                    continue;
                }

                System.out.printf("- %s %,d원 %d개 %s\n", name, price, proQuantity, promotion.getName());
                System.out.printf("- %s %,d원 %d개\n", name, price, quantity);
                continue;
            }

            if (quantity == 0) {
                System.out.printf("- %s %,d원 재고 없음\n", name, price);
                continue;
            }

            System.out.printf("- %s %,d원 %d개\n", name, price, quantity);
        }
        System.out.println();
    }


    public static void printReceipt(Result result) {
        Map<Item, Integer> finalPurchaseItems = result.getFinalPurchaseItems();
        Map<Item, Integer> presentItems = result.getPresentItems();

        System.out.println("==========W 편의점==========");
        System.out.println("상품명          수량     금액");
        for (Item item : finalPurchaseItems.keySet()) {
            System.out.printf("%-15s%-7d%,d\n", item.getName(), finalPurchaseItems.get(item), item.getPrice() * finalPurchaseItems.get(item));
        }
        System.out.println("==========증   정==========");
        for (Item item : presentItems.keySet()) {
            System.out.printf("%-15s%-7d\n", item.getName(), presentItems.get(item));
        }
        System.out.println("==========================");
        System.out.printf("총구매액        %-7d%,d\n", result.getTotalQuantity(), result.getTotalPrice());
        System.out.printf("행사할인               -%,d\n", result.getPromotionPrice());
        System.out.printf("멤버십할인             -%,d\n", result.getMembershipDiscountPrice());
        System.out.printf("내실돈                %,d\n", result.getFinalPrice());
    }
}
