package co.wadcorp.waiting.data.domain.settings;

import co.wadcorp.waiting.data.domain.settings.OperationTimeSettingsData.AutoPauseSettings;
import co.wadcorp.waiting.data.domain.settings.OperationTimeSettingsData.AutoPauseSettings.PauseReason;
import co.wadcorp.waiting.data.domain.settings.OperationTimeSettingsData.OperationTimeForDay;
import co.wadcorp.waiting.data.enums.OperationDay;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class DefaultOperationTimeSettingDataFactory {

  private static final String PAUSE_REASON_UUID = "9gcl4qqmT9OcuVYdKd_Pzw";

  /**
   * OperationTimeForDay
   * */
  private static final LocalTime OPERATION_START_TIME = LocalTime.of(10, 0, 0);
  private static final LocalTime OPERATION_END_TIME = LocalTime.of(20, 0 ,0);

  /**
   * AutoPauseSettings
   * */
  private static final LocalTime AUTO_PAUSE_START_TIME = LocalTime.of(14,0,0);
  private static final LocalTime AUTO_PAUSE_END_TIME = LocalTime.of(15,0,0);

  public static OperationTimeSettingsData create() {
    return OperationTimeSettingsData.builder()
        .operationTimeForDays(createOperationTimeForAllDay())
        .autoPauseSettings(createAutoPauseSettings())
        .isUsedAutoPause(false)
        .build();
  }

  private static List<OperationTimeForDay> createOperationTimeForAllDay() {
    return Arrays.stream(OperationDay.values())
        .map(DefaultOperationTimeSettingDataFactory::createOperationTimeForDay)
        .toList();
  }

  private static OperationTimeForDay createOperationTimeForDay(OperationDay day) {
    return OperationTimeForDay.builder()
        .day(String.valueOf(day))
        .operationStartTime(OPERATION_START_TIME)
        .operationEndTime(OPERATION_END_TIME)
        .isClosedDay(false)
        .build();
  }

  private static AutoPauseSettings createAutoPauseSettings() {
    return AutoPauseSettings.builder()
        .autoPauseStartTime(AUTO_PAUSE_START_TIME)
        .autoPauseEndTime(AUTO_PAUSE_END_TIME)
        .pauseReasons(List.of(createPauseReason()))
        .build();
  }

  public static PauseReason createPauseReason() {
    return new PauseReason(PAUSE_REASON_UUID, true, "웨이팅이 잠시 정지되었어요. 잠시만 기다려주세요.");
  }

}
