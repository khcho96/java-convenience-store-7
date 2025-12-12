package store.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class InputParser {

    private static final String FIRST_DELIMITER = ",";
    private static final String SECOND_DELIMITER = "-";
    private static final String YES = "Y";

    private InputParser() {
    }

    public static List<String> parsePurchaseProducts(String rawInput) {
        Validator.validateNullOrBlank(rawInput);
        rawInput = rawInput.strip();

        Validator.validateCsvFormat(rawInput);

        List<String> purchaseProducts = new ArrayList<>();
        String[] split = rawInput.split(FIRST_DELIMITER);
        for (String s : split) {
            String[] order = s.strip().substring(1, s.length() - 1).split(SECOND_DELIMITER);
            String name = order[0];
            int quantity = NumberConvertor.convertToNumber(order[1]);

            Validator.validateQuantity(quantity);

            for (int i = 0; i < quantity; i++) {
                purchaseProducts.add(name);
            }
        }

        return purchaseProducts;
    }

    public static boolean parseChoice(String rawChoice) {
        Validator.validateNullOrBlank(rawChoice);
        rawChoice = rawChoice.strip();
        Validator.validateChoiceFormat(rawChoice);
        return rawChoice.equals(YES);
    }
}
