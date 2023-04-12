package co.wadcorp.waiting.data.domain.settings;

import co.wadcorp.waiting.data.domain.settings.OperationTimeSettingsData.AutoPauseSettings.PauseReason;
import co.wadcorp.waiting.data.domain.settings.OperationTimeSettingsData.OperationTimeForDay;
import co.wadcorp.waiting.data.domain.waiting.RegistrableStatus;
import co.wadcorp.waiting.data.enums.OperationDay;
import co.wadcorp.waiting.data.support.BaseEntity;
import co.wadcorp.waiting.data.support.BooleanYnConverter;
import co.wadcorp.waiting.data.support.OperationTimeSettingsConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = false, exclude = "seq")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Table(name = "cw_operation_time_settings")
public class OperationTimeSettingsEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "seq")
  private Long seq;

  @Column(name = "shop_id")
  private String shopId;

  @Column(name = "publish_yn", columnDefinition = "char")
  @Convert(converter = BooleanYnConverter.class)
  private Boolean isPublished;

  @Column(name = "data", columnDefinition = "text")
  @Convert(converter = OperationTimeSettingsConverter.class)
  private OperationTimeSettingsData operationTimeSettingsData;

  @Builder
  public OperationTimeSettingsEntity(String shopId, OperationTimeSettingsData operationTimeSettingsData) {
    this.shopId = shopId;
    this.isPublished = true;
    this.operationTimeSettingsData = operationTimeSettingsData;
  }

  public void unPublish() {
    this.isPublished = false;
  }

  public List<OperationTimeForDay> getOperationTimeForDays() {
    return this.operationTimeSettingsData.getOperationTimeForDays();
  }

  public OperationTimeForDay findOperationTimeForDay(String day) {
    return this.operationTimeSettingsData.findOperationTimeForDay(OperationDay.valueOf(day));
  }

  public Boolean getIsUsedAutoPause() {
    return this.operationTimeSettingsData.getIsUsedAutoPause();
  }

  public LocalTime getAutoPauseStartTime() {
    return this.operationTimeSettingsData.getAutoPauseSettings().getAutoPauseStartTime();
  }

  public LocalTime getAutoPauseEndTime() {
    return this.operationTimeSettingsData.getAutoPauseSettings().getAutoPauseEndTime();
  }

  public PauseReason getDefaultPauseReason() {
    return this.operationTimeSettingsData.getDefaultPauseReason();
  }

  public RegistrableStatus findRegistrableStatus(LocalDate operationDate) {

    DayOfWeek dayOfWeek = operationDate.getDayOfWeek();
    OperationTimeForDay operationTimeForDay = this.findOperationTimeForDay(dayOfWeek.name());

    // 영업일이라면 OPEN 그 외 CLOSED
    return operationTimeForDay.isOpened() ? RegistrableStatus.OPEN : RegistrableStatus.CLOSED;
  }

  public PauseReason findReason(String pauseReasonId) {
    return this.operationTimeSettingsData.findReason(pauseReasonId);
  }

}
