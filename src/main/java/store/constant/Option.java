package store.constant;

import java.util.Arrays;

public enum Option {

    YES("Y"),
    NO("N"),
    ;

    private final String name;

    Option(String name) {
        this.name = name;
    }

    public static Option from(String name) {
        return Arrays.stream(values())
                .filter(option -> option.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.FORMAT_ERROR.getErrorMessage()));
    }
}
