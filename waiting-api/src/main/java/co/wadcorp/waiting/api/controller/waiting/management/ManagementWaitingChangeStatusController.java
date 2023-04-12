package co.wadcorp.waiting.api.controller.waiting.management;

import co.wadcorp.waiting.api.model.waiting.request.CancelWaitingByManagementRequest;
import co.wadcorp.waiting.api.service.waiting.WaitingManagementApiService;
import co.wadcorp.waiting.data.api.ApiResponse;
import co.wadcorp.waiting.shared.util.OperationDateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class ManagementWaitingChangeStatusController {

    private final WaitingManagementApiService waitingManagementApiService;

    /**
     * 웨이팅 관리 - 호출
     *
     * @param shopId
     * @param waitingId
     * @return
     */
    @PostMapping(value = "/api/v1/shops/{shopId}/management/waiting/{waitingId}/call")
    public ApiResponse<?> calling(@PathVariable String shopId, @PathVariable String waitingId) {
        LocalDate operationDate = OperationDateUtils.getOperationDateFromNow();
        LocalDateTime currentDateTime = LocalDateTime.now();

        waitingManagementApiService.call(shopId, waitingId, operationDate, currentDateTime);

        return ApiResponse.ok();
    }

    /**
     * 웨이팅 관리 - 착석
     *
     * @param shopId
     * @param waitingId
     * @return
     */
    @PostMapping(value = "/api/v1/shops/{shopId}/management/waiting/{waitingId}/sitting")
    public ApiResponse<?> sitting(@PathVariable String shopId, @PathVariable String waitingId) {
        LocalDate operationDate = OperationDateUtils.getOperationDateFromNow();

        waitingManagementApiService.sitting(shopId, waitingId, operationDate);

        return ApiResponse.ok();
    }

    /**
     * 웨이팅 관리 - 취소
     *
     * @param shopId
     * @param waitingId
     * @return
     */
    @PostMapping(value = "/api/v1/shops/{shopId}/management/waiting/{waitingId}/cancel")
    public ApiResponse<?> cancel(@PathVariable String shopId, @PathVariable String waitingId,
                                 @RequestBody CancelWaitingByManagementRequest request) {
        waitingManagementApiService.cancel(shopId, waitingId, request.cancelReason(), OperationDateUtils.getOperationDateFromNow());

        return ApiResponse.ok();
    }
}
