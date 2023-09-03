package cn.gson.crm.common;

public class ApiResult<T> {
    private boolean success;
    private String resultCode;
    private String resultMsg;
    private T data;

    public ApiResult(boolean success, String resultCode, String resultMsg) {
        this.success = success;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public ApiResult(boolean success, String resultCode, String resultMsg, T data) {
        this.success = success;
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.data = data;
    }

    public static ApiResult success() {
        return new ApiResult(true, "200", "success");
    }

    public static ApiResult failure(String resultCode, String resultMsg) {
        return new ApiResult(false, resultCode, resultMsg);
    }

    public static ApiResult failure(ApiResultEnum resultEnum) {
        return new ApiResult(false, resultEnum.code, resultEnum.message);
    }


    public static <T> ApiResult success(T data) {
        return new ApiResult(true, "200", "success", data);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
