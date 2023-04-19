package co.wadcorp.waiting.data.domain.waiting;

import co.wadcorp.waiting.data.domain.settings.OperationTimeSettingsData.AutoPauseSettings.PauseReason;
import co.wadcorp.waiting.data.domain.settings.OperationTimeSettingsData.OperationTimeForDay;
import co.wadcorp.waiting.data.domain.settings.OperationTimeSettingsEntity;
import co.wadcorp.waiting.data.domain.waiting.ShopOperationInfoEntity.ShopOperationInfoEntityBuilder;
import co.wadcorp.waiting.shared.util.OperationDateTimeUtils;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShopOperationInitializeFactory {

  public static ShopOperationInfoEntity initialize(String shopId, LocalDate operationDate,
      OperationTimeSettingsEntity operationTimeSettings) {
    ShopOperationInfoEntityBuilder operationInfoEntityBuilder = ShopOperationInfoEntity.builder();

    // 일시 정지 상태 - 일시 정지 정보 설정
    if (operationTimeSettings.getIsUsedAutoPause()) {
      PauseReason defaultPauseReason = operationTimeSettings.getDefaultPauseReason();

      operationInfoEntityBuilder.autoPauseInfo(AutoPauseInfo.builder()
          .autoPauseStartDateTime(OperationDateTimeUtils.getCalculateOperationDateTime(operationDate,
              operationTimeSettings.getAutoPauseStartTime()))
          .autoPauseEndDateTime(
              OperationDateTimeUtils.getCalculateOperationDateTime(operationDate, operationTimeSettings.getAutoPauseEndTime())
          )
          .autoPauseReasonId(defaultPauseReason.getId())
          .autoPauseReason(defaultPauseReason.getReason())
          .build()
      );
    }

    // 운영 시간 설정
    RegistrableStatus registrableStatus = operationTimeSettings.findRegistrableStatus(
        operationDate);
    if (registrableStatus == RegistrableStatus.CLOSED) {
      operationInfoEntityBuilder.closedReason(ClosedReason.CLOSED_DAY);
    }

    OperationTimeForDay operationTimeForDay = operationTimeSettings.findOperationTimeForDay(
        operationDate.getDayOfWeek().name()
    );

    return operationInfoEntityBuilder
        .shopId(shopId)
        .operationDate(operationDate)
        .registrableStatus(registrableStatus)
        .operationStartDateTime(
            OperationDateTimeUtils.getCalculateOperationDateTime(operationDate, operationTimeForDay.getOperationStartTime())
        )
        .operationEndDateTime(
            OperationDateTimeUtils.getCalculateOperationDateTime(operationDate, operationTimeForDay.getOperationEndTime())
        )
        .build();
  }
}
