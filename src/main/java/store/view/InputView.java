package store.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    private static final String PURCHASE_PRODUCTS_REQUEST = "구매하실 상품명과 수량을 입력해주세요. (예: [사이다-2],[감자칩-1])";

    public static String readPurchaseProducts() {
        System.out.println(PURCHASE_PRODUCTS_REQUEST);
        return Console.readLine();
    }
}
