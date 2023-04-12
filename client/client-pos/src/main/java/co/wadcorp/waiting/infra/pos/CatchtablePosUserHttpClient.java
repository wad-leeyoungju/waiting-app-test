package co.wadcorp.waiting.infra.pos;

import co.wadcorp.waiting.infra.pos.dto.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.Map;

@HttpExchange(contentType = MediaType.APPLICATION_JSON_VALUE)
public interface CatchtablePosUserHttpClient {

    @GetExchange(url = "/catchpos/api/v1/shops/{shopId}/user-info")
    PosApiResponse<PosUserResponse> getUserInfo(@RequestHeader Map<String, Object> header,
                                                @PathVariable("shopId") String shopId);

    @PostExchange(url = "/catchpos/api/v1/user/update-email/request")
    PosApiResponse<PosResultResponse> updateEmailRequest(@RequestHeader Map<String, Object> header,
                                                         @RequestBody UpdatePosUserEmailRequest request);

    @PutExchange(url = "/catchpos/api/v1/user/password/update")
    PosApiResponse<PosResultResponse> updatePassword(@RequestHeader Map<String, Object> header,
                                                     @RequestBody UpdatePosUserPasswordRequest request);

    @PostExchange(url = "/catchpos/api/v1/user/update-phone/cert-no/request")
    PosApiResponse<PosResultResponse> updatePhoneNumberRequest(@RequestHeader Map<String, Object> header,
                                                               @RequestBody UpdatePosUserPhoneNumberRequest request);

    @PostExchange(url = "/catchpos/api/v1/user/update-phone/cert-no/confirm")
    PosApiResponse<UpdatePosUserPhoneNumberResponse> updatePhoneNumberConfirm(
            @RequestHeader Map<String, Object> header,
            @RequestBody UpdatePosUserPhoneNumberRequest request
    );
}
