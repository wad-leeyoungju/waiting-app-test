package co.wadcorp.waiting.data.domain.settings;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class AlarmSettingsData {

  private static final int DEFAULT_AUTO_CANCEL_PERIOD = 3;
  private static final boolean DEFAULT_IS_USED_AUTO_CANCEL = true;
  private static final int DEFAULT_AUTO_ALARM_ORDERING = 1;
  private static final boolean DEFAULT_IS_AUTO_ENTER_ALARM = true;

  private Integer autoCancelPeriod;
  private Boolean isUsedAutoCancel;
  private Integer autoAlarmOrdering;
  private Boolean isAutoEnterAlarm;

  private AlarmSettingsData() {
  }

  public static AlarmSettingsData of(Integer autoCancelPeriod, Boolean isUsedAutoCancel,
      Integer autoAlarmOrdering, Boolean isAutoEnterAlarm) {
    AlarmSettingsDataValidator.validate(autoCancelPeriod, autoAlarmOrdering);

    AlarmSettingsData result = new AlarmSettingsData();
    result.autoCancelPeriod = autoCancelPeriod;
    result.isUsedAutoCancel = isUsedAutoCancel;
    result.autoAlarmOrdering = autoAlarmOrdering;
    result.isAutoEnterAlarm = isAutoEnterAlarm;

    return result;
  }

  /**
   * <pre>
   * AlarmSettingsData의 isAutoEnterAlarm 필드는 뒤늦게 추가된 필드이기 때문에 기존에 저장된 데이터에는 이 필드가 존재하지 않는다.
   * 따라서 다음과 같은 문제가 발생할 수 있다.
   * 1. 알림설정 데이터 저장 시 DB 테이블에 데이터가 추가될 때 기존 데이터의 publish 여부 값이 변경(Y->N)되면서 update 가 발생하는데
   *    이 때 기존에 저장되어 있던 값의 data 컬럼에 {..., isAutoEnterAlarm: null} 값으로 데이터가 update 되는 현상이 발생
   * 2. isAutoEnterAlarm 필드가 없는 데이터를 조회해서 사용하려고 할 때 문제가 발생
   *
   * 이러한 문제를 방지하기 위해 필드가 없거나 필드가 있더라도 값이 null 인 경우는 default 값(true)를 세팅해준다.
   * </pre>
   */
  public void setDefaultIsAutoEnterAlarmIfNotExist() {
    if(doesNeedToSetDefault()) {
      setDefaultIsAutoEnterAlarm();
    }
  }

  private boolean doesNeedToSetDefault() {
    try {
      this.getClass().getDeclaredField("isAutoEnterAlarm");

      if(this.isAutoEnterAlarm == null) {
        // 필드가 있어도 값이 null 일 때
        return true;
      }
    } catch (NoSuchFieldException e) {
      // 필드가 없을 때
      return true;
    }
    return false;
  }

  private void setDefaultIsAutoEnterAlarm() {
    this.isAutoEnterAlarm = DEFAULT_IS_AUTO_ENTER_ALARM;
  }

}
