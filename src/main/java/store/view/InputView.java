package store.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    private static final String PURCHASE_PRODUCTS_REQUEST = "구매하실 상품명과 수량을 입력해주세요. (예: [사이다-2],[감자칩-1])";
    private static final String FREE_PRODUCTS_REQUEST = "\n현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";

    public static String readPurchaseProducts() {
        System.out.println(PURCHASE_PRODUCTS_REQUEST);
        return Console.readLine();
    }

    public static String readFreeProductChoice(String name, int free) {
        System.out.printf(FREE_PRODUCTS_REQUEST + "\n", name, free);
        return Console.readLine();
    }
}
