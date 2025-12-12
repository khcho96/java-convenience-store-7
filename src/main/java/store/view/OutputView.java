package store.view;

import java.util.Map;
import store.domain.Product;
import store.dto.ReceiptDto;
import store.dto.StockDto;

public class OutputView {

    private static final String WELL_COME_MESSAGE = "\n안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n";

    public static void printStock(StockDto stock) {
        System.out.println(WELL_COME_MESSAGE);
        System.out.println(stock.stock());
    }

    public static void printErrorMessage(IllegalArgumentException e) {
        System.out.println(e.getMessage());
    }

    public static void printReceipt(ReceiptDto receiptDto) {
        Map<Product, Integer> purchaseProducts = receiptDto.purchaseProducts();
        Map<Product, Integer> presentProducts = receiptDto.presentProducts();
        int membershipDiscountAmount = receiptDto.membershipDiscountAmount();

        int totalPurchaseQuantity = 0;
        int totalPurchasePrice = 0;
        int totalPresentPrice = 0;


        System.out.println("\n============W 편의점============");
        System.out.println("상품명            수량      금액");
        for (Product product : purchaseProducts.keySet()) {
            int quantity = purchaseProducts.get(product);
            int price = product.getPrice(quantity);
            System.out.printf("%-16s%-8s%,d\n", product.getName(), quantity, price);

            totalPurchaseQuantity += quantity;
            totalPurchasePrice += price;
        }

        System.out.println("============증   정============");
        for (Product product : presentProducts.keySet()) {
            int quantity = presentProducts.get(product);
            int price = product.getPrice(quantity);
            System.out.printf("%-16s%d\n",  product.getName(), quantity);

            totalPresentPrice += price;
        }

        int finalPrice = totalPurchasePrice - totalPresentPrice - membershipDiscountAmount;
        System.out.println("==============================");
        System.out.printf("%-15s%-8s%,d\n", "총구매액", totalPurchaseQuantity, totalPurchasePrice);
        System.out.printf("%-23s-%,d\n", "행사할인", totalPresentPrice);
        System.out.printf("%-23s-%,d\n", "멤버십할인", membershipDiscountAmount);
        System.out.printf("%-24s%,d\n", "내실돈", finalPrice);
    }
}
