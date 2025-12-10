package store.util;

import static store.constant.ErrorMessage.FORMAT_ERROR;
import static store.constant.ErrorMessage.INVALID_ERROR;

public final class Validator {

    private static final String CSV_FORMAT = "^ *(\\[[가-힣a-zA-Z]+-\\d+])+ *(, *(\\[[가-힣a-zA-Z]+-\\d+])+ *)*$";

    private Validator() {}

    public static void validateNullOrBlank(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(INVALID_ERROR.getErrorMessage());
        }
    }

    public static void validateCsvFormat(String input) {
        if (!input.matches(CSV_FORMAT)) {
            throw new IllegalArgumentException(FORMAT_ERROR.getErrorMessage());
        }
    }

    public static void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(INVALID_ERROR.getErrorMessage());
        }
    }
}
