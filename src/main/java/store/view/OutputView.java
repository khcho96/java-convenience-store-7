package store.view;

import store.dto.StockDto;

public class OutputView {

    private static final String WELL_COME_MESSAGE = "안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n";

    public static void printStock(StockDto stock) {
        System.out.println(WELL_COME_MESSAGE);
        System.out.println(stock.stock());
    }

    public static void printResult() {

    }

    public static void printErrorMessage(IllegalArgumentException e) {
        System.out.println(e.getMessage());
    }
}
