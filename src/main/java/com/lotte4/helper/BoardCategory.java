package com.lotte4.helper;

public enum BoardCategory {

    MEMBER("회원", "/cs/faq/list?cate1=member", SubCategory.MEMBER_SUB),
    COUPON_EVENT("쿠폰/이벤트", "/cs/faq/list?cate1=coupon", SubCategory.COUPON_EVENT_SUB),
    ORDER_PAYMENT("주문/결제", "/cs/faq/list?cate1=order", SubCategory.ORDER_PAYMENT_SUB),
    DELIVERY("배송", "/cs/faq/list?cate1=delivery", SubCategory.DELIVERY_SUB),
    CANCEL_RETURN_EXCHANGE("취소/반품/교환", "/cs/faq/list?cate1=cancel", SubCategory.CANCEL_RETURN_EXCHANGE_SUB),
    TRAVEL("여행/숙박/항공", "/cs/faq/list?cate1=travel", SubCategory.TRAVEL_SUB),
    SAFE_TRADE("안전거래", "/cs/faq/list?cate1=safe", SubCategory.SAFE_TRADE_SUB);

    private final String displayName;
    private final String url;
    private final SubCategory subCategory;  // 배열 대신 Enum 값 자체를 사용

    BoardCategory(String displayName, String url, SubCategory subCategory) {
        this.displayName = displayName;
        this.url = url;
        this.subCategory = subCategory;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUrl() {
        return url;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    // 하위 카테고리 Enum
    public enum SubCategory {
        MEMBER_SUB("회원가입", "회원등급", "회원정보", "회원탈퇴"),
        COUPON_EVENT_SUB("쿠폰", "이벤트"),
        ORDER_PAYMENT_SUB("주문", "결제"),
        DELIVERY_SUB("신청/이용방법", "변경/해지"),
        CANCEL_RETURN_EXCHANGE_SUB("취소", "반품", "교환"),
        TRAVEL_SUB("여행", "숙박", "항공"),
        SAFE_TRADE_SUB("안전거래");

        private final String[] subCategoryNames;

        // 가변인자를 사용하여 배열을 바로 생성
        SubCategory(String... subCategoryNames) {
            this.subCategoryNames = subCategoryNames;
        }

        public String[] getSubCategoryNames() {
            return subCategoryNames;
        }

    }
}
