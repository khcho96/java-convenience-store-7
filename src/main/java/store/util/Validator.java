package store.util;

import store.constant.ErrorMessage;

public final class Validator {

    private static final String DOUBLE_DELIMITER_FORMAT = "^([가-힣]+-[1-9]\\d*) *(, *([가-힣]+-[1-9]\\d*) *)*$";

    private Validator() {}

    public static void validateCsvFormat(String input) {
        if (!input.matches(DOUBLE_DELIMITER_FORMAT)) {
            throw new IllegalArgumentException(ErrorMessage.FORMAT_ERROR.getErrorMessage());
        }
    }
}
