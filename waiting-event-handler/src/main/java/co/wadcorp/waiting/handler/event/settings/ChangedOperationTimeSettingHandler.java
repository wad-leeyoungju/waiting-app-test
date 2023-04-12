package co.wadcorp.waiting.handler.event.settings;

import co.wadcorp.waiting.data.domain.settings.OperationTimeSettingsData.AutoPauseSettings.PauseReason;
import co.wadcorp.waiting.data.domain.settings.OperationTimeSettingsData.OperationTimeForDay;
import co.wadcorp.waiting.data.domain.settings.OperationTimeSettingsEntity;
import co.wadcorp.waiting.data.domain.waiting.ShopOperationInfoEntity;
import co.wadcorp.waiting.data.domain.waiting.ShopOperationInfoHistoryEntity;
import co.wadcorp.waiting.data.domain.waiting.ShopOperationInfoHistoryRepository;
import co.wadcorp.waiting.data.enums.OperationDay;
import co.wadcorp.waiting.data.event.settings.ChangedOperationTimeSettingsEvent;
import co.wadcorp.waiting.data.service.settings.OperationTimeSettingsService;
import co.wadcorp.waiting.data.service.waiting.ShopOperationInfoService;
import co.wadcorp.waiting.shared.util.OperationDateTimeUtils;
import co.wadcorp.waiting.shared.util.OperationDateUtils;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class ChangedOperationTimeSettingHandler {

  private final ShopOperationInfoService shopOperationInfoService;
  private final OperationTimeSettingsService operationTimeSettingsService;
  private final ShopOperationInfoHistoryRepository shopOperationInfoHistoryRepository;

  @TransactionalEventListener(ChangedOperationTimeSettingsEvent.class)
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void changeShopOperationInfo(ChangedOperationTimeSettingsEvent event) {
    LocalDate operationDate = OperationDateUtils.getOperationDateFromNow();

    OperationTimeSettingsEntity operationTimeSettings =
        operationTimeSettingsService.getOperationTimeSettings(event.shopId());

    List<ShopOperationInfoEntity> operationInfoEntities = shopOperationInfoService.findByShopIdAndOperationDateAfterOrEqual(
        event.shopId(), operationDate
    );

    boolean isChangeInOperationTime =
        operationTimeSettingsService.isThereChangeInOperationTime(event.shopId());

    operationInfoEntities.forEach(operationInfo -> {
      updateInfos(operationInfo.getOperationDate(), operationTimeSettings, operationInfo, isChangeInOperationTime);

      shopOperationInfoHistoryRepository.save(ShopOperationInfoHistoryEntity.of(operationInfo));
    });
  }

  private void updateInfos(LocalDate operationDate,
      OperationTimeSettingsEntity operationTimeSettings,
      ShopOperationInfoEntity shopOperationInfoEntity,
      boolean isChangeInOperationTime) {
    setAutoPauseSettings(operationDate, operationTimeSettings, shopOperationInfoEntity);

    OperationTimeForDay operationTimeForDay = operationTimeSettings.findOperationTimeForDay(
        OperationDay.find(operationDate.getDayOfWeek().name()));

    shopOperationInfoEntity.settingOperationStatus(operationTimeForDay);

    if(isChangeInOperationTime) {
      ZonedDateTime operationStartDateTime = OperationDateTimeUtils.getCalculateOperationDateTime(
          operationDate, operationTimeForDay.getOperationStartTime());
      ZonedDateTime operationEndDateTime = OperationDateTimeUtils.getCalculateOperationDateTime(
          operationDate, operationTimeForDay.getOperationEndTime());

      shopOperationInfoEntity.updateOperationDateTime(operationStartDateTime, operationEndDateTime);
    }
  }

  private void setAutoPauseSettings(LocalDate operationDate,
      OperationTimeSettingsEntity operationTimeSettings,
      ShopOperationInfoEntity shopOperationInfoEntity) {
    if (operationTimeSettings.getIsUsedAutoPause()) {
      ZonedDateTime autoPauseStartDateTime = OperationDateTimeUtils.getCalculateOperationDateTime(
          operationDate, operationTimeSettings.getAutoPauseStartTime());
      ZonedDateTime autoPauseEndDateTime = OperationDateTimeUtils.getCalculateOperationDateTime(
          operationDate, operationTimeSettings.getAutoPauseEndTime());
      PauseReason defaultPauseReason = operationTimeSettings.getDefaultPauseReason();

      shopOperationInfoEntity.pauseAuto(autoPauseStartDateTime, autoPauseEndDateTime,
          defaultPauseReason.getId(), defaultPauseReason.getReason());
      return;
    }

    shopOperationInfoEntity.clearAutoPauseInfo();
  }
}
