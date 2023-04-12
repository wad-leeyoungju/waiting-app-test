package co.wadcorp.waiting.api.model.waiting.vo;

import co.wadcorp.waiting.data.domain.waiting.RegistrableStatus;
import co.wadcorp.waiting.data.domain.waiting.ShopOperationInfoEntity;
import co.wadcorp.waiting.data.query.waiting.dto.ShopOperationInfoDto;
import java.time.ZonedDateTime;
import java.util.Objects;

public enum OperationStatus {

  OPEN("영업 중"),
  BY_PASS("바로 입장"),
  PAUSE("일시 중지"),
  CLOSED("영업 종료");

  private final String value;

  OperationStatus(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static OperationStatus find(ShopOperationInfoDto shopOperationInfoDto,
      ZonedDateTime nowDateTime) {
    return find(
        shopOperationInfoDto.getRegistrableStatus(),
        shopOperationInfoDto.getOperationStartDateTime(),
        shopOperationInfoDto.getOperationEndDateTime(),
        shopOperationInfoDto.getAutoPauseStartDateTime(),
        shopOperationInfoDto.getAutoPauseEndDateTime(),
        shopOperationInfoDto.getManualPauseStartDateTime(),
        shopOperationInfoDto.getManualPauseEndDateTime(),
        nowDateTime
    );
  }

  public static OperationStatus find(ShopOperationInfoEntity shopOperationInfoEntity,
      ZonedDateTime nowDateTime) {
    return find(
        shopOperationInfoEntity.getRegistrableStatus(),
        shopOperationInfoEntity.getOperationStartDateTime(),
        shopOperationInfoEntity.getOperationEndDateTime(),
        shopOperationInfoEntity.getAutoPauseStartDateTime(),
        shopOperationInfoEntity.getAutoPauseEndDateTime(),
        shopOperationInfoEntity.getManualPauseStartDateTime(),
        shopOperationInfoEntity.getManualPauseEndDateTime(),
        nowDateTime
    );
  }
  private static OperationStatus find(RegistrableStatus registrableStatus,
      ZonedDateTime operationStartDateTime, ZonedDateTime operationEndDateTime,
      ZonedDateTime autoPauseStartDateTime, ZonedDateTime autoPauseEndDateTime,
      ZonedDateTime manualPauseStartDateTime, ZonedDateTime manualPauseEndDateTime,
      ZonedDateTime nowDateTime) {

    if (registrableStatus == RegistrableStatus.CLOSED) {
      return CLOSED;
    }

    if (registrableStatus == RegistrableStatus.BY_PASS) {
      return BY_PASS;
    }

    if (nowDateTime.isBefore(operationStartDateTime) || nowDateTime.isAfter(operationEndDateTime)) {
      return CLOSED;
    }

    if (Objects.nonNull(manualPauseStartDateTime)
        && Objects.nonNull(manualPauseEndDateTime)
        && nowDateTime.isAfter(manualPauseStartDateTime)
        && nowDateTime.isBefore(manualPauseEndDateTime)) {
      return PAUSE;
    }

    if (Objects.nonNull(manualPauseStartDateTime)
        && Objects.isNull(manualPauseEndDateTime)
        && nowDateTime.isAfter(manualPauseStartDateTime)) {
      return PAUSE;
    }

    if (Objects.nonNull(autoPauseStartDateTime)
        && Objects.nonNull(autoPauseEndDateTime)
        && nowDateTime.isAfter(autoPauseStartDateTime)
        && nowDateTime.isBefore(autoPauseEndDateTime)) {
      return PAUSE;
    }

    return OPEN;
  }

}
