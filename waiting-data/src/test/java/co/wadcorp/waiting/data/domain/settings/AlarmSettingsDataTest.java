package co.wadcorp.waiting.data.domain.settings;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AlarmSettingsDataTest {

  @Test
  @DisplayName("입장안내알림 사용 여부가 정해져 있을 때 설정된 값 그대로 있어야 한다.")
  void setDefaultIsAutoEnterAlarmIfNotExist() {
    //Given
    AlarmSettingsData settingsData1 = AlarmSettingsData.of(3, true,
        3, true);
    AlarmSettingsData settingsData2 = AlarmSettingsData.of(3, true,
        3, false);

    //When
    settingsData1.setDefaultIsAutoEnterAlarmIfNotExist();
    settingsData2.setDefaultIsAutoEnterAlarmIfNotExist();

    //Then
    assertTrue(settingsData1.getIsAutoEnterAlarm());
    assertFalse(settingsData2.getIsAutoEnterAlarm());
  }

  @Test
  @DisplayName("입장안내알림 사용 여부가 null일 때 default 값(true)가 세팅되어야 한다.")
  void setDefaultIsAutoEnterAlarmIfNotExist_null() {
    //Given
    AlarmSettingsData settingsData = AlarmSettingsData.of(3, true, 3, null);

    //When
    settingsData.setDefaultIsAutoEnterAlarmIfNotExist();

    //Then
    assertTrue(settingsData.getIsAutoEnterAlarm());
  }

  @Test
  @DisplayName("입장안내알림 사용 여부 필드가 없을 때 default 값(true)가 세팅되어야 한다.")
  void setDefaultIsAutoEnterAlarmIfNotExist_empty() {
    //Given
    String alarmSettingsDataJsonWithNoField = "{"
        + "    \"autoCancelPeriod\": 3, "
        + "    \"isUsedAutoCancel\": true, "
        + "    \"autoAlarmOrdering\": 3 "
        + "}";

    ObjectMapper objectMapper = new ObjectMapper();

    try {
      AlarmSettingsData alarmSettingsData = objectMapper.readValue(alarmSettingsDataJsonWithNoField,
          AlarmSettingsData.class);

      //When
      alarmSettingsData.setDefaultIsAutoEnterAlarmIfNotExist();

      //Then
      assertTrue(alarmSettingsData.getIsAutoEnterAlarm());
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

}