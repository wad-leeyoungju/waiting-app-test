package co.wadcorp.waiting.api.controller.waiting.register;

import co.wadcorp.waiting.api.controller.waiting.register.dto.request.WaitingRegisterRequest;
import co.wadcorp.waiting.api.model.waiting.response.WaitingRegisterResponse;
import co.wadcorp.waiting.api.service.waiting.WaitingRegisterApiService;
import co.wadcorp.waiting.data.api.ApiResponse;
import co.wadcorp.waiting.shared.util.OperationDateUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterWaitingController {

  private final WaitingRegisterApiService waitingRegisterApiService;

  /**
   * 웨이팅 등록 전 전화번호로 중복/3회초과 여부 검증
   *
   * @param shopId
   * @return
   */
  @GetMapping(value = "/api/v1/shops/{shopId}/register/waiting/validation")
  public ApiResponse<?> checkWaitingBeforeRegisterByPhone(@PathVariable String shopId, @RequestParam String customerPhone) {
    waitingRegisterApiService.checkWaitingBeforeRegisterByPhone(
        shopId,
        customerPhone,
        OperationDateUtils.getOperationDateFromNow()
    );
    return ApiResponse.ok();
  }

  /**
   * 웨이팅 등록
   *
   * @param shopId
   * @return 웨이팅 채번
   */
  @PostMapping(value = "/api/v1/shops/{shopId}/register/waiting")
  public ApiResponse<WaitingRegisterResponse> registerWaiting(@PathVariable String shopId,
      @Valid @RequestBody WaitingRegisterRequest waitingRegisterRequest) {
    return ApiResponse.ok(
        waitingRegisterApiService.registerWaiting(shopId, OperationDateUtils.getOperationDateFromNow(), waitingRegisterRequest));
  }

}
