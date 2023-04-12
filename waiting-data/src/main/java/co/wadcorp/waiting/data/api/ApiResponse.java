package co.wadcorp.waiting.data.api;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

import java.io.Serializable;
import java.util.Map;

@JsonPropertyOrder({
    "resultCode",
    "displayMessage",
    "message",
    "data"
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> implements Serializable {
    public ApiResponse(T data) {
        this.setResultCode("200");
        this.setData(data);
    }

    public ApiResponse(String resultCode, T data) {
        this.setResultCode(resultCode);
        this.setData(data);
    }

    /**
     * 응답 코드.
     */
    private String resultCode;

    /**
     * displayMessage의 의도는, 이용자에게 노출 가능한 메시지를 담아두려는 것이다. 이용자에게 노출해야 할 문구가 있다면 displayMessage에 저장하도록 한다.
     */
    private String displayMessage;

    private String message;

    private T data;

    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.<T>builder().resultCode("200").data(data)
                .build();
    }

    public static <T> ApiResponse<T> ok(T data, String displayMessage) {
        return ApiResponse.<T>builder().resultCode("200").displayMessage(displayMessage)
                .build();
    }

    public static ApiResponse ok() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResultCode("200");
        apiResponse.setData(Map.of("success", true));
        return apiResponse;
    }
}
