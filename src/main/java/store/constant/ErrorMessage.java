package store.constant;

public enum ErrorMessage {

    ITEMS_FORMAT_ERROR("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    NO_EXIST_ITEMS_ERROR("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    EMPTY_ITEMS_ERROR("재고 수량을 초과하여 구매할 수 없습니다."),
    FORMAT_ERROR("잘못된 입력입니다. 다시 입력해 주세요."),
    ;

    private static final String ERROR_MESSAGE_PREFIX = "[ERROR] ";
    private final String errorMessage;

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(Object... args) {
        return ERROR_MESSAGE_PREFIX + String.format(errorMessage, args);
    }
}
