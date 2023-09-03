package cn.gson.crm.common;

public enum ApiResultEnum {
    INVALID_ACCESS("4001", "invalid access"),
    USER_NOT_FOUND("4002", "user not found"),
    ROLE_NOT_FOUND("4003", "role not found");
    String code;
    String message;

    ApiResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
