package store.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    private static final String PURCHASE_PRODUCTS_REQUEST = "구매하실 상품명과 수량을 입력해주세요. (예: [사이다-2],[감자칩-1])";
    private static final String FREE_PRODUCTS_REQUEST = "\n현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n";
    private static final String IMPOSSIBLE_PROMOTION_REQUEST = "\n현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n";
        private static final String MEMBERSHIP_REQUEST = "\n멤버십 할인을 받으시겠습니까? (Y/N)";

    public static String readPurchaseProducts() {
        System.out.println(PURCHASE_PRODUCTS_REQUEST);
        return Console.readLine();
    }

    public static String readFreeProductChoice(String name, int free) {
        System.out.printf(FREE_PRODUCTS_REQUEST, name, free);
        return Console.readLine();
    }

    public static String readImpossiblePromotionChoice(String name, int impossiblePromotionQuantity) {
        System.out.printf(IMPOSSIBLE_PROMOTION_REQUEST, name, impossiblePromotionQuantity);
        return Console.readLine();
    }

    public static String readMembershipChoice() {
        System.out.println(MEMBERSHIP_REQUEST);
        return Console.readLine();
    }
}
