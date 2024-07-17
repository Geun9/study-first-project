package jieun.util;

public enum ErrorCode {
    MENU_OPTION_NOT_FOUND_EXCEPTION(1001, "❗️잘못된 메뉴 번호입니다. 다시 입력해주세요.❗️"),
    PRODUCT_ID_NOT_FOUND_EXCEPTION(1006, "❗️잘못된 상품 번호입니다. 다시 입력해주세요.❗️"),
    PRODUCT_NOT_FOUND_EXCEPTION(1002, "❗️존재하지 않는 상품 번호입니다. 다시 입력해주세요.❗️"),
    NULL_PRODUCT_INFO_EXCEPTION(1003, "❗️상품 정보는 빈값일 수 없습니다.❗️"),
    PRODUCT_PRICE_OUT_OF_RANGE_EXCEPTION(1004, "❗️상품 가격이 유효 범위를 벗어났습니다.❗️\n❗️0원부터 999,999원 사이의 가격을 입력해주세요.❗️"),
    PRODUCT_STOCK_OUT_OF_RANGE_EXCEPTIO(1005, "❗️상품 재고가 유효 범위를 벗어났습니다.❗️\n❗️0개부터 999개 사이의 재고를 입력해주세요.❗️");

    private int code;
    private String msg;

    private ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "ErrorCode: " + code +
            "\t" + "ErrorMessage: "+ msg;
    }
}
