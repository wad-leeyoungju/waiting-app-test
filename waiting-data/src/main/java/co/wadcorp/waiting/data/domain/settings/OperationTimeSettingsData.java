package co.wadcorp.waiting.data.domain.settings;

import co.wadcorp.waiting.data.domain.settings.OperationTimeSettingsData.AutoPauseSettings.PauseReason;
import co.wadcorp.waiting.data.enums.OperationDay;
import co.wadcorp.waiting.data.exception.AppException;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationTimeSettingsData {

  private List<OperationTimeForDay> operationTimeForDays;
  private Boolean isUsedAutoPause;
  private AutoPauseSettings autoPauseSettings;

  /**
   * 2개의 OperationTimeSettingsData를 비교해서 운영시간이 동일한지 체크
   * @param compareData
   * @return
   */
  public boolean isSameOperationTimeForDays(OperationTimeSettingsData compareData) {
    List<OperationTimeForDay> compareOperationTimeForDays = compareData.getOperationTimeForDays();

    return this.operationTimeForDays.stream().allMatch(operationTimeForDay -> {
      Optional<OperationTimeForDay> sameDayDataOpt = compareOperationTimeForDays.stream()
          .filter(timeData -> timeData.getDay().equals(operationTimeForDay.getDay())).findFirst();

      if (sameDayDataOpt.isEmpty()) {
        return false;
      }

      OperationTimeForDay sameData = sameDayDataOpt.get();
      if (sameData.getOperationStartTime().compareTo(operationTimeForDay.getOperationStartTime())
          != 0) {
        return false;
      }

      if (sameData.getOperationEndTime().compareTo(operationTimeForDay.getOperationEndTime())
          != 0) {
        return false;
      }

      return true;
    });
  }

  public List<PauseReason> getPauseReasons() {
    return this.autoPauseSettings.pauseReasons;
  }

  public LocalTime getAutoPauseStartTime() {
    return this.autoPauseSettings.getAutoPauseStartTime();
  }

  public LocalTime getAutoPauseEndTime() {
    return this.autoPauseSettings.getAutoPauseEndTime();
  }

  public OperationTimeForDay findOperationTimeForDay(OperationDay day) {
    return operationTimeForDays.stream()
        .filter(item -> day.isSameDay(item.getDay()))
        .findFirst()
        .orElseThrow(
            () -> new AppException(
                HttpStatus.BAD_REQUEST, String.format("Illegal args for day : [%s]", day)));
  }

  public PauseReason getDefaultPauseReason() {
    return this.autoPauseSettings.getDefaultPauseReason();
  }

  public PauseReason findReason(String pauseReasonId) {
    return this.autoPauseSettings.findReason(pauseReasonId);
  }


  @EqualsAndHashCode
  @Getter
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class OperationTimeForDay {

    private String day;
    private LocalTime operationStartTime;
    private LocalTime operationEndTime;
    private Boolean isClosedDay;

    public boolean isOpened() {
      return !isClosedDay;
    }

  }

  @EqualsAndHashCode
  @Getter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class AutoPauseSettings {
    private LocalTime autoPauseStartTime;
    private LocalTime autoPauseEndTime;
    private List<PauseReason> pauseReasons;

    public Boolean isPaused(LocalTime localTime) {
      return localTime.isAfter(autoPauseStartTime) && localTime.isBefore(autoPauseEndTime);
    }

    public PauseReason getDefaultPauseReason() {
      return pauseReasons.stream()
          .filter(item -> item.isDefault)
          .findFirst()
          .orElseGet(DefaultOperationTimeSettingDataFactory::createPauseReason);
    }

    public PauseReason findReason(String pauseReasonId) {
      return pauseReasons.stream()
          .filter(item -> item.getId().equals(pauseReasonId))
          .findFirst()
          .orElseThrow(
              () -> new AppException(HttpStatus.BAD_REQUEST, "일시 중지 사유를 찾을 수 없습니다. 다시 시도해주세요.")
          );
    }

    @EqualsAndHashCode
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PauseReason {
      private String id;
      private Boolean isDefault;
      private String reason;
    }
  }

}
