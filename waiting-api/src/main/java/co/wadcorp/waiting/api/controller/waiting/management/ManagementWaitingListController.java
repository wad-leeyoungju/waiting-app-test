package co.wadcorp.waiting.api.controller.waiting.management;

import co.wadcorp.waiting.api.model.waiting.request.WaitingListRequest;
import co.wadcorp.waiting.api.model.waiting.response.WaitingListResponse;
import co.wadcorp.waiting.api.service.waiting.WaitingListApiService;
import co.wadcorp.waiting.data.api.ApiResponse;
import co.wadcorp.waiting.shared.util.OperationDateUtils;
import co.wadcorp.waiting.shared.util.ZonedDateTimeUtils;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ManagementWaitingListController {

  private final WaitingListApiService service;

  /**
   * 웨이팅 목록 조회
   *
   * @param shopId
   * @return
   */
  @GetMapping("/api/v1/shops/{shopId}/management/waiting")
  public ApiResponse<WaitingListResponse> currentStatusDefault(
      @PathVariable("shopId") String shopId, @RequestHeader("X-CTM-AUTH") String ctmAuth,
      WaitingListRequest request) {
    LocalDate operationDate = OperationDateUtils.getOperationDateFromNow();
    ZonedDateTime nowDateTime = ZonedDateTimeUtils.nowOfSeoul();

    if (request.isTableMode()) {
      return ApiResponse.ok(
          service.getTableWaitingList(shopId, operationDate, nowDateTime, request)
      );
    }

    return ApiResponse.ok(
        service.getDefaultWaitingList(shopId, operationDate, nowDateTime, request)
    );
  }
}
