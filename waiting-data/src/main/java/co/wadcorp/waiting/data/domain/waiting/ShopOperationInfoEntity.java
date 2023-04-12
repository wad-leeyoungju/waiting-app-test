package co.wadcorp.waiting.data.domain.waiting;

import co.wadcorp.waiting.data.domain.settings.OperationTimeSettingsData.OperationTimeForDay;
import co.wadcorp.waiting.data.support.BaseEntity;
import co.wadcorp.waiting.shared.util.ZonedDateTimeUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "cw_shop_operation_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopOperationInfoEntity extends BaseEntity {

  public static final ShopOperationInfoEntity EMPTY_OPERATION_INFO = new ShopOperationInfoEntity();

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long seq;

  @Column(name = "shop_id")
  private String shopId;

  @Column(name = "operation_date")
  private LocalDate operationDate;

  @Column(name = "registrable_status")
  @Enumerated(EnumType.STRING)
  private RegistrableStatus registrableStatus;

  @Column(name = "operation_start_date_time")
  private ZonedDateTime operationStartDateTime;

  @Column(name = "operation_end_date_time")
  private ZonedDateTime operationEndDateTime;

  @Embedded
  private ManualPauseInfo manualPauseInfo;

  @Embedded
  private AutoPauseInfo autoPauseInfo;

  @Column(name = "closed_reason")
  @Enumerated(EnumType.STRING)
  private ClosedReason closedReason;

  @Builder
  private ShopOperationInfoEntity(Long seq, String shopId, LocalDate operationDate,
      RegistrableStatus registrableStatus, ZonedDateTime operationStartDateTime,
      ZonedDateTime operationEndDateTime, ManualPauseInfo manualPauseInfo,
      AutoPauseInfo autoPauseInfo, ClosedReason closedReason) {
    this.seq = seq;
    this.shopId = shopId;
    this.operationDate = operationDate;
    this.registrableStatus = registrableStatus;
    this.operationStartDateTime = operationStartDateTime;
    this.operationEndDateTime = operationEndDateTime;
    this.manualPauseInfo = manualPauseInfo;
    this.autoPauseInfo = autoPauseInfo;
    this.closedReason = closedReason;
  }

  public void open() {
    this.registrableStatus = RegistrableStatus.OPEN;
    this.closedReason = null;
    this.manualPauseInfo = null;
  }

  public void close() {
    this.registrableStatus = RegistrableStatus.CLOSED;
    this.closedReason = ClosedReason.MANUAL;
    this.manualPauseInfo = null;
  }

  public void byPass() {
    this.registrableStatus = RegistrableStatus.BY_PASS;
    this.closedReason = null;
    this.manualPauseInfo = null;
  }

  public void closeByClosedDay() {
    this.registrableStatus = RegistrableStatus.CLOSED;
    this.closedReason = ClosedReason.CLOSED_DAY;
    this.manualPauseInfo = null;
  }

  public void pause(String pauseReasonId, String pauseReason, Integer pausePeriod) {
    ZonedDateTime pauseStart = ZonedDateTimeUtils.nowOfSeoul();
    ZonedDateTime pauseEnd = pausePeriod == -1 ? null : pauseStart.plusMinutes(pausePeriod);

    this.registrableStatus = RegistrableStatus.OPEN;
    this.closedReason = null;

    this.manualPauseInfo = ManualPauseInfo.builder()
        .manualPauseStartDateTime(pauseStart)
        .manualPauseEndDateTime(pauseEnd)
        .manualPauseReasonId(pauseReasonId)
        .manualPauseReason(pauseReason)
        .build();
  }

  public void pauseAuto(ZonedDateTime pauseStart, ZonedDateTime pauseEnd, String pauseReasonId,
      String pauseReason) {

    this.registrableStatus = RegistrableStatus.OPEN;
    this.closedReason = null;

    this.autoPauseInfo = AutoPauseInfo.builder()
        .autoPauseStartDateTime(pauseStart)
        .autoPauseEndDateTime(pauseEnd)
        .autoPauseReasonId(pauseReasonId)
        .autoPauseReason(pauseReason)
        .build();
  }

  public ZonedDateTime getManualPauseStartDateTime() {
    if (this.manualPauseInfo == null) {
      return null;
    }
    return this.manualPauseInfo.getManualPauseStartDateTime();
  }

  public ZonedDateTime getManualPauseEndDateTime() {
    if (this.manualPauseInfo == null) {
      return null;
    }
    return this.manualPauseInfo.getManualPauseEndDateTime();
  }

  public String getManualPauseReasonId() {
    if (this.manualPauseInfo == null) {
      return null;
    }
    return this.manualPauseInfo.getManualPauseReasonId();
  }

  public String getManualPauseReason() {
    if (this.manualPauseInfo == null) {
      return null;
    }
    return this.manualPauseInfo.getManualPauseReason();
  }

  public ZonedDateTime getAutoPauseStartDateTime() {
    if (this.autoPauseInfo == null) {
      return null;
    }
    return this.autoPauseInfo.getAutoPauseStartDateTime();
  }

  public ZonedDateTime getAutoPauseEndDateTime() {
    if (this.autoPauseInfo == null) {
      return null;
    }
    return this.autoPauseInfo.getAutoPauseEndDateTime();
  }

  public String getAutoPauseReasonId() {
    if (this.autoPauseInfo == null) {
      return null;
    }
    return this.autoPauseInfo.getAutoPauseReasonId();
  }

  public String getAutoPauseReason() {
    if (this.autoPauseInfo == null) {
      return null;
    }
    return this.autoPauseInfo.getAutoPauseReason();
  }

  public void updateOperationDateTime(ZonedDateTime operationStartDateTime,
      ZonedDateTime operationEndDateTime) {

    updateOperationStartDateTime(operationStartDateTime);
    updateOperationEndDateTime(operationEndDateTime);
  }

  public void updateOperationStartDateTime(ZonedDateTime operationStartDateTime) {
    this.operationStartDateTime = operationStartDateTime;
  }

  public void updateOperationEndDateTime(ZonedDateTime operationEndDateTime) {
    this.operationEndDateTime = operationEndDateTime;
  }

  public boolean isBeforeOperationDateTime(ZonedDateTime nowLocalDateTime) {
    return nowLocalDateTime.isBefore(this.operationStartDateTime);
  }

  public boolean isAfterOperationEndDateTime(ZonedDateTime nowLocalDateTime) {
    return nowLocalDateTime.isAfter(this.operationEndDateTime);
  }

  public boolean isBetweenAutoPauseRange(ZonedDateTime nowLocalDateTime) {
    if (autoPauseInfo == null) {
      return false;
    }
    return autoPauseInfo.isBetweenAutoPauseRange(nowLocalDateTime);
  }

  public void clearAutoPauseInfo() {
    autoPauseInfo = null;
  }

  public ClosedReason findClosedReason(ZonedDateTime nowDateTime) {
    if (Objects.isNull(this.closedReason)
        && !ZonedDateTimeUtils.isBetween(nowDateTime, operationStartDateTime, operationEndDateTime)) {
      return ClosedReason.OPERATION_HOUR_CLOSED;
    }
    return this.closedReason;
  }

  public void settingOperationStatus(OperationTimeForDay operationTimeForDay) {
    if (operationTimeForDay.getIsClosedDay()) {
      this.closeByClosedDay();
      return;
    }
    this.open();
  }

}
