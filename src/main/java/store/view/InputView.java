package store.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public static String readMenuOption() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return Console.readLine();
    }
}
